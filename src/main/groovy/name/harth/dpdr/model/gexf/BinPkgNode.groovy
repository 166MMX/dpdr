package name.harth.dpdr.model.gexf

class BinPkgNode extends PkgNode
{
    // Attributes
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
    // DPB:  Source
    SrcPkgNode source

    // DPB:  Depends
    List<BinPkgNode> depends = new ArrayList<BinPkgNode>()

    // DPB:  Pre-Depends
    List<BinPkgNode> preDepends = new ArrayList<BinPkgNode>()

    // DPB:  Recommends
    List<BinPkgNode> recommends = new ArrayList<BinPkgNode>()

    // DPB:  Suggests
    List<BinPkgNode> suggests = new ArrayList<BinPkgNode>()

    // DPB:  Enhances
    List<BinPkgNode> enhances = new ArrayList<BinPkgNode>()

    // DPB:  Breaks
    List<BinPkgNode> breaks = new ArrayList<BinPkgNode>()

    // DPB:  Conflicts
    List<BinPkgNode> conflicts = new ArrayList<BinPkgNode>()

    // DPB:  Built-Using
    List<BinPkgNode> builtUsing = new ArrayList<BinPkgNode>()
}
