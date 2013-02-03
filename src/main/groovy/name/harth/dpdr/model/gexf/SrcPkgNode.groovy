package name.harth.dpdr.model.gexf

class SrcPkgNode extends Node
{
    // Attributes
    // DP:   Format (mandatory)
    String format

    // DP:   DM-Upload-Allowed
    boolean debianMaintainersUploadAllowed

    // DP:   Standards-Version (recommended)
    String standardsVersion

    // DP:   Vcs-Browser, Vcs-Git, et al.
    String vcsBrowser

    // DP:   Checksums-Sha1 (recommended)
    boolean checksumsSha1

    // DP:   Checksums-Sha256 (recommended)
    boolean checksumsSha256

    // DP:   Files (mandatory)
    int files


    // Relationships
    // DP:   Uploaders
    List<MaintainerNode> uploaders

    // DP:   Binary
    List<BinPkgNode> binary

    // DP:   Build-Depends
    List<BinPkgNode> buildDepends

    // DP:   Build-Depends-Indep
    List<BinPkgNode> buildDependsIndep

    // DP:   Build-Conflicts
    List<BinPkgNode> buildConflicts

    // DP:   Build-Conflicts-Indep
    List<BinPkgNode> buildConflictsIndep
}
