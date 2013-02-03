package name.harth.dpdr.model.gexf

class PkgNode extends Node
{
    // Attributes
    // DPS:  Version (mandatory)
    // DPB:  Version (mandatory)
    String version

    // RFS:  Section (optional)
    // DPB:  Section (recommended)
    String section

    // RFS:  Priority (optional)
    // DPB:  Priority (recommended)
    String priority

    // DPS:  Architecture
    // DPB:  Architecture (mandatory)
    String architecture

    // DPS:  Homepage
    // DPB:  Homepage
    String homepage


    // Relationships
    // DPS:  Maintainer (mandatory)
    // DPB:  Maintainer (mandatory)
    MaintainerNode maintainer
}
