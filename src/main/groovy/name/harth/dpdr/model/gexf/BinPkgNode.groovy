package name.harth.dpdr.model.gexf

class BinPkgNode extends Node
{
    // Attributes
    // DP:   Source
    SrcPkgNode source

    // DP:   Essential
    boolean essential

    // DP:   Installed-Size
    int installedSize

    // DP:   Description (mandatory)
    String shortDescription
    String longDescription

    // RF:   Filename (mandatory)
    String filename

    // RF:   Size (mandatory)
    int size

    // RF:   MD5Sum (recommended)
    boolean md5Sum

    // RF:   SHA1 (recommended)
    boolean sha1

    // RF:   SHA256 (recommended)
    boolean sha256

    // RF:   Description-md5 (optional)
    boolean descriptionMd5


    // Relationships
    // DPB:  Depends
    List<BinPkgNode> depends

    // DPB:  Pre-Depends
    List<BinPkgNode> preDepends

    // DPB:  Recommends
    List<BinPkgNode> recommends

    // DPB:  Suggests
    List<BinPkgNode> suggests

    // DPB:  Enhances
    List<BinPkgNode> enhances

    // DPB:  Breaks
    List<BinPkgNode> breaks

    // DPB:  Conflicts
    List<BinPkgNode> conflicts

    // DPB:   Built-Using
    List<BinPkgNode> builtUsing
}
