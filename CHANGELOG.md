# Changelog

All notable changes to this project are documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/), and this project
adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

_Nothing user facing yet._

## [0.0.4] - 2026-09-02

_There is no 0.0.3 release: that version was prepared in the repository but never reached Maven
Central. Its only change, the migration to Java 21, is part of this release._

### Changed

- **Java 21 is now required.** Earlier releases were built for Java 17, so running this one on 17
  fails with an `UnsupportedClassVersionError`.
- **The Java module name is now `io.github.amanzat.util`.** It was previously derived from the file
  name as `java.utils`, so a modular project has to update its `requires` clause.
- `ExceptionUtils.getCause`, `StringUtils.concat` and `IOUtils.toInputStream` accept a `null`
  argument instead of throwing a `NullPointerException`.

### Added

- `Automatic-Module-Name` in the jar manifest.
- Argument validation in `CollectionUtils.chunkify`, `IOUtils.copy` and
  `StringUtils.truncateWithMarker`, which now throw an `IllegalArgumentException` rather than
  hanging or failing from inside the JDK.

### Fixed

- `FileUtils.ensureDirectoryExists` returned `true` when the path existed but was a regular file,
  and now returns `false`.
- `CollectionUtils.chunkify` never terminated when the chunk size was zero.
- `IOUtils.copy` never terminated when given a zero buffer size or a zero length buffer.
- `StringUtils.truncateWithMarker` threw a `StringIndexOutOfBoundsException` for a negative maximum
  length.

## [0.0.2] - 2026-01-30

### Changed

- No functional changes: the utility classes are identical to 0.0.1. This release only updates the
  build tooling and the build time dependency versions.

### Added

- The project name in the published POM.

## [0.0.1] - 2024-01-31

### Added

- Initial release, built for Java 17: `CollectionUtils`, `ComparableUtils`, `DateTimeUtils`,
  `ExceptionUtils`, `FileUtils`, `IOUtils`, `NumberUtils`, `ObjectUtils`, `Slf4jUtils`,
  `StringUtils` and `ThreadUtils`.

[Unreleased]: https://github.com/amanzat/java-utils/compare/v0.0.4...dev
[0.0.4]: https://github.com/amanzat/java-utils/compare/v0.0.2...v0.0.4
[0.0.2]: https://github.com/amanzat/java-utils/compare/v0.0.1...v0.0.2
[0.0.1]: https://github.com/amanzat/java-utils/releases/tag/v0.0.1
