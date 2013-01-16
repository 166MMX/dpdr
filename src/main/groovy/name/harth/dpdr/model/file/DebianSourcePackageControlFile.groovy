package name.harth.dpdr.model.file

import name.harth.dpdr.model.paragraph.DebianBinaryPackageParagraph
import name.harth.dpdr.model.paragraph.DebianSourcePackageParagraph

class DebianSourcePackageControlFile {
    DebianSourcePackageParagraph sourceParagraph
    DebianBinaryPackageParagraph[] binaryParagraphs
}
