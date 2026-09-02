#!/usr/bin/env python3
"""Turns the [Unreleased] section of CHANGELOG.md into a release and bumps the README install snippet.

    release-notes.py check                 fail when [Unreleased] has no release notes or a file has drifted
    release-notes.py apply VERSION DATE    rewrite CHANGELOG.md and README.md for the release

Both modes validate everything first and write nothing on failure. They print previous=<version>, the
latest release heading in the changelog, and append it to $GITHUB_OUTPUT when that is set.
"""
import os
import re
import sys
from pathlib import Path

CHANGELOG = Path("CHANGELOG.md")
README = Path("README.md")
REPO = "https://github.com/amanzat/java-utils"
UNRELEASED = "## [Unreleased]"
PLACEHOLDER = "_Nothing user facing yet._"
RELEASE_HEADING = re.compile(r"^## \[(\d+\.\d+\.\d+)\] - \d{4}-\d{2}-\d{2}$")
VERSION = re.compile(r"^\d+\.\d+\.\d+$")
DATE = re.compile(r"^\d{4}-\d{2}-\d{2}$")
# the <version> that directly follows the java-utils <artifactId> in the install snippet, nothing else
README_VERSION = re.compile(r"(<artifactId>java-utils</artifactId>\n\s*<version>)(\d+\.\d+\.\d+)(</version>)")


def fail(message):
    print(f"::error::{message}")
    sys.exit(1)


def as_tuple(version):
    return tuple(int(part) for part in version.split("."))


def read_changelog():
    """Returns (lines, unreleased index, next heading index, previous version)."""
    lines = CHANGELOG.read_text().split("\n")
    if lines.count(UNRELEASED) != 1:
        fail(f"{CHANGELOG}: expected exactly one '{UNRELEASED}' heading, found {lines.count(UNRELEASED)}")
    start = lines.index(UNRELEASED)
    end = next((i for i in range(start + 1, len(lines)) if lines[i].startswith("## ")), None)
    if end is None:
        fail(f"{CHANGELOG}: no release heading after '{UNRELEASED}'")
    match = RELEASE_HEADING.match(lines[end])
    if not match:
        fail(f"{CHANGELOG}:{end + 1}: expected '## [X.Y.Z] - YYYY-MM-DD', found '{lines[end]}'")
    return lines, start, end, match.group(1)


def release_notes(body):
    """The section body without the placeholder, with blank lines collapsed; None when there are no notes."""
    notes = []
    for line in body:
        if line.strip() == PLACEHOLDER:
            continue
        if line.strip() == "" and (not notes or notes[-1] == ""):
            continue
        notes.append(line)
    while notes and notes[-1] == "":
        notes.pop()
    # real notes are at least one "- " entry under a "### " subsection, so a reworded placeholder does not pass
    if not any(line.startswith("### ") for line in notes) or not any(line.startswith("- ") for line in notes):
        return None
    return notes


def check_links(lines, previous):
    unreleased_link = f"[Unreleased]: {REPO}/compare/v{previous}...dev"
    if lines.count(unreleased_link) != 1:
        fail(f"{CHANGELOG}: expected exactly one link line '{unreleased_link}'")
    return lines.index(unreleased_link)


def read_readme():
    text = README.read_text()
    matches = README_VERSION.findall(text)
    if len(matches) != 1:
        fail(f"{README}: expected exactly one java-utils <version> in the install snippet, found {len(matches)}")
    return text, matches[0][1]


def report_previous(previous):
    print(f"previous={previous}")
    if output := os.environ.get("GITHUB_OUTPUT"):
        with open(output, "a") as file:
            file.write(f"previous={previous}\n")


def check():
    lines, start, end, previous = read_changelog()
    check_links(lines, previous)
    if release_notes(lines[start + 1:end]) is None:
        fail(f"{CHANGELOG}: the {UNRELEASED} section has no release notes, add them before releasing")
    read_readme()
    report_previous(previous)
    print(f"{CHANGELOG}: {UNRELEASED} has release notes, latest release is {previous}")


def apply(version, date):
    if not VERSION.match(version):
        fail(f"'{version}' is not a X.Y.Z version")
    if not DATE.match(date):
        fail(f"'{date}' is not a YYYY-MM-DD date")
    lines, start, end, previous = read_changelog()
    check_links(lines, previous)
    notes = release_notes(lines[start + 1:end])
    if notes is None:
        fail(f"{CHANGELOG}: the {UNRELEASED} section has no release notes, add them before releasing")
    if as_tuple(version) <= as_tuple(previous):
        fail(f"{CHANGELOG}: releasing {version}, but the latest release in the changelog is already {previous}")
    readme, readme_version = read_readme()

    heading = f"## [{version}] - {date}"
    lines[start:end] = [UNRELEASED, "", PLACEHOLDER, "", heading, "", *notes, ""]
    link = check_links(lines, previous)
    lines[link:link + 1] = [
        f"[Unreleased]: {REPO}/compare/v{version}...dev",
        f"[{version}]: {REPO}/compare/v{previous}...v{version}",
    ]
    CHANGELOG.write_text("\n".join(lines))
    README.write_text(README_VERSION.sub(rf"\g<1>{version}\g<3>", readme, count=1))
    report_previous(previous)
    print(f"{CHANGELOG}: {UNRELEASED} released as '{heading}', {len(notes)} lines of notes")
    print(f"{README}: install snippet {readme_version} -> {version}")


if __name__ == "__main__":
    if sys.argv[1:] == ["check"]:
        check()
    elif len(sys.argv) == 4 and sys.argv[1] == "apply":
        apply(sys.argv[2], sys.argv[3])
    else:
        print(__doc__, end="")
        sys.exit(2)
