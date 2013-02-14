package name.harth.dpdr.io.gexf

import groovy.xml.XmlUtil
import name.harth.dpdr.model.gexf.MaintainerNode
import name.harth.dpdr.model.gexf.Node
import groovy.xml.StreamingMarkupBuilder
import name.harth.dpdr.model.gexf.SrcPkgNode

class GexfWriter
{
    public static final int NODE_ATTR_INTERNET_ADDRESS = 0
    public static final int NODE_ATTR_CHECKSUMS_SHA1 = 1
    public static final int NODE_ATTR_CHECKSUMS_SHA256 = 2
    public static final int NODE_ATTR_DM_UPLOAD_ALLOWED = 3
    public static final int NODE_ATTR_FILES = 4
    public static final int NODE_ATTR_FORMAT = 5
    public static final int NODE_ATTR_STANDARDS_VERSION = 6
    public static final int NODE_ATTR_VCS_BROWSER = 7
    public static final int NODE_ATTR_ARCHITECTURE = 8
    public static final int NODE_ATTR_HOMEPAGE = 9
    public static final int NODE_ATTR_PRIORITY = 10
    public static final int NODE_ATTR_SECTION = 11
    public static final int NODE_ATTR_VERSION = 12
    public static final int NODE_ATTR_TYPE = 13
    public static final String NODE_ATTR_TYPE_MAINTAINER = 'maintainer'
    public static final String NODE_ATTR_TYPE_SOURCE_PACKAGE = 'sourcePackage'
    public static final String NODE_ATTR_TYPE_BINARY_PACKAGE = 'binaryPackage'
    public static final int EDGE_ATTR_MAINTAINER_TYPE = 0
    public static final String EDGE_ATTR_MAINTAINER_TYPE_MAINTAINER = 'maintainer'
    public static final String EDGE_ATTR_MAINTAINER_TYPE_UPLOADER = 'uploader'

    static void write (File file, List<Node> nodeList)
    {
        def doc = {
            mkp.xmlDeclaration()
            mkp.declareNamespace('': 'http://www.gexf.net/1.2draft')
            mkp.declareNamespace('xsi': 'http://www.w3.org/2001/XMLSchema-instance')
            mkp.declareNamespace('viz': 'http://www.gexf.net/1.1draft/viz')
            gexf(version: '1.2', 'xsi:schemaLocation':'http://www.gexf.net/1.2draft http://www.gexf.net/1.2draft/gexf.xsd') {
                meta(lastmodifieddate: String.format('%tF', new Date())) {
                    creator {
                        mkp.yield 'Creator signature'
                    }
                    description {
                        mkp.yield 'Some random description'
                    }
                }
                graph(mode: 'static', defaultedgetype: 'directed', idtype: 'integer') {
                    attributes(class:'node', mode:'static') {
                        attribute(id:  NODE_ATTR_INTERNET_ADDRESS,   type:  'string',      title:  'e-mail Address')
                        attribute(id:  NODE_ATTR_CHECKSUMS_SHA1,     type:  'boolean',     title:  'SHA1 checksums')
                        attribute(id:  NODE_ATTR_CHECKSUMS_SHA256,   type:  'boolean',     title:  'SHA256 checksums')
                        attribute(id:  NODE_ATTR_DM_UPLOAD_ALLOWED,  type:  'boolean',     title:  'Debian maintainers upload allowed')
                        attribute(id:  NODE_ATTR_FILES,              type:  'integer',     title:  'Files')
                        attribute(id:  NODE_ATTR_FORMAT,             type:  'string',      title:  'Source package format') {
                            options {
                                mkp.yield '1.0|3.0 (native)|3.0 (quilt)'
                            }
                        }
                        attribute(id:  NODE_ATTR_STANDARDS_VERSION,  type:  'string',      title:  'Standards version')
                        attribute(id:  NODE_ATTR_VCS_BROWSER,        type:  'string',      title:  'VCS web interface')
                        attribute(id:  NODE_ATTR_ARCHITECTURE,       type:  'liststring',  title:  'Architecture') {
                            options {
                                mkp.yield 'all|alpha|amd64|any|any-amd64|any-i386|arm|armeb|armel|armhf|avr32|darwin-alpha|darwin-amd64|darwin-avr32|darwin-hppa|darwin-i386|darwin-ia64|darwin-m32r|darwin-m68k|darwin-mips|darwin-mipsel|darwin-powerpc|darwin-ppc64|darwin-s390|darwin-s390x|darwin-sh3|darwin-sh3eb|darwin-sh4|darwin-sh4eb|darwin-sparc|freebsd-alpha|freebsd-amd64|freebsd-avr32|freebsd-hppa|freebsd-i386|freebsd-ia64|freebsd-m32r|freebsd-m68k|freebsd-mips|freebsd-mipsel|freebsd-powerpc|freebsd-ppc64|freebsd-s390|freebsd-s390x|freebsd-sh3|freebsd-sh3eb|freebsd-sh4|freebsd-sh4eb|freebsd-sparc|hppa|hurd-i386|i386|ia64|kfreebsd-alpha|kfreebsd-amd64|kfreebsd-any|kfreebsd-avr32|kfreebsd-hppa|kfreebsd-i386|kfreebsd-ia64|kfreebsd-m32r|kfreebsd-m68k|kfreebsd-mips|kfreebsd-mipsel|kfreebsd-powerpc|kfreebsd-ppc64|kfreebsd-s390|kfreebsd-s390x|kfreebsd-sh3|kfreebsd-sh3eb|kfreebsd-sh4|kfreebsd-sh4eb|kfreebsd-sparc|knetbsd-alpha|knetbsd-amd64|knetbsd-avr32|knetbsd-hppa|knetbsd-i386|knetbsd-ia64|knetbsd-m32r|knetbsd-m68k|knetbsd-mips|knetbsd-mipsel|knetbsd-powerpc|knetbsd-ppc64|knetbsd-s390|knetbsd-s390x|knetbsd-sh3|knetbsd-sh3eb|knetbsd-sh4|knetbsd-sh4eb|knetbsd-sparc|kopensolaris-alpha|kopensolaris-amd64|kopensolaris-avr32|kopensolaris-hppa|kopensolaris-i386|kopensolaris-ia64|kopensolaris-m32r|kopensolaris-m68k|kopensolaris-mips|kopensolaris-mipsel|kopensolaris-powerpc|kopensolaris-ppc64|kopensolaris-s390|kopensolaris-s390x|kopensolaris-sh3|kopensolaris-sh3eb|kopensolaris-sh4|kopensolaris-sh4eb|kopensolaris-sparc|linux-any|lpia|m32r|m68k|mips|mipsel|netbsd-alpha|netbsd-amd64|netbsd-avr32|netbsd-hppa|netbsd-i386|netbsd-ia64|netbsd-m32r|netbsd-m68k|netbsd-mips|netbsd-mipsel|netbsd-powerpc|netbsd-ppc64|netbsd-s390|netbsd-s390x|netbsd-sh3|netbsd-sh3eb|netbsd-sh4|netbsd-sh4eb|netbsd-sparc|openbsd-alpha|openbsd-amd64|openbsd-avr32|openbsd-hppa|openbsd-i386|openbsd-ia64|openbsd-m32r|openbsd-m68k|openbsd-mips|openbsd-mipsel|openbsd-powerpc|openbsd-ppc64|openbsd-s390|openbsd-s390x|openbsd-sh3|openbsd-sh3eb|openbsd-sh4|openbsd-sh4eb|openbsd-sparc|powerpc|powerpcspe|ppc64|s390|s390x|sh|sh3|sh3eb|sh4|sh4eb|solaris-alpha|solaris-amd64|solaris-avr32|solaris-hppa|solaris-i386|solaris-ia64|solaris-m32r|solaris-m68k|solaris-mips|solaris-mipsel|solaris-powerpc|solaris-ppc64|solaris-s390|solaris-s390x|solaris-sh3|solaris-sh3eb|solaris-sh4|solaris-sh4eb|solaris-sparc|sparc|sparc64|ucalpha|ucamd64|ucavr32|uchppa|uci386|ucia64|ucm32r|ucm68k|ucmips|ucmipsel|ucpowerpc|ucppc64|ucs390|ucs390x|ucsh3|ucsh3eb|ucsh4|ucsh4eb|ucsparc'
                            }
                        }
                        attribute(id:  NODE_ATTR_HOMEPAGE,           type:  'string',      title:  'Homepage')
                        attribute(id:  NODE_ATTR_PRIORITY,           type:  'string',      title:  'Priority') {
                            options {
                                mkp.yield 'required|important|standard|optional|extra'
                            }
                        }
                        attribute(id:  NODE_ATTR_SECTION,            type:  'string',      title:  'Section') {
                            options {
                                mkp.yield 'admin|cli-mono|comm|database|devel|doc|editors|electronics|embedded|fonts|games|gnome|gnu-r|gnustep|graphics|hamradio|haskell|httpd|interpreters|java|kde|kernel|libdevel|libs|lisp|localization|mail|math|misc|net|news|ocaml|oldlibs|otherosfs|perl|php|python|ruby|science|Section|shells|sound|tex|text|utils|vcs|video|web|x11|xfce'
                            }
                        }
                        attribute(id:  NODE_ATTR_VERSION,            type:  'string',      title:  'Version')
                        attribute(id:  NODE_ATTR_TYPE,               type:  'string',      title:  'Node type') {
                            options {
                                mkp.yield NODE_ATTR_TYPE_MAINTAINER + '|' + NODE_ATTR_TYPE_SOURCE_PACKAGE + '|' + NODE_ATTR_TYPE_BINARY_PACKAGE
                            }
                        }
                    }
                    attributes(class:'edge', mode:'static') {
                        attribute(id:  EDGE_ATTR_MAINTAINER_TYPE,    type:  'string',      title:  'Maintainer type') {
                            options {
                                mkp.yield EDGE_ATTR_MAINTAINER_TYPE_MAINTAINER + '|' + EDGE_ATTR_MAINTAINER_TYPE_UPLOADER
                            }
                        }
                    }
                    nodes(count: nodeList.size()) {
                        buildNodes(it, nodeList)
                    }
                    def edgeResult = buildEdges(it, nodeList)
                    edges(count: edgeResult.count) {
                        edgeResult.nodes
                    }
                }
            }
        }

        StreamingMarkupBuilder builder = new StreamingMarkupBuilder()
        builder.encoding = 'UTF-8'
        def object = builder.bind(doc)
        file.text = XmlUtil.serialize(object)
    }

    static def buildEdges(def root, List<Node> nodeList)
    {
        int counter = 0;
        def result = nodeList.each { model ->
            if (model instanceof MaintainerNode)
            {
                return
            }
            else if (model instanceof SrcPkgNode)
            {
                root.edge(id: counter++, source: model.maintainer.id, target: model.id) {
                    attvalues {
                        attvalue(for:  EDGE_ATTR_MAINTAINER_TYPE,     value:  EDGE_ATTR_MAINTAINER_TYPE_MAINTAINER)
                    }
                }
                model.uploaders.each { uploader ->
                    root.edge(id: counter++, source: uploader.id, target: model.id) {
                        attvalues {
                            attvalue(for:  EDGE_ATTR_MAINTAINER_TYPE,     value:  EDGE_ATTR_MAINTAINER_TYPE_UPLOADER)
                        }
                    }
                }
            }
        }
        return [
            count: counter,
            nodes: result
        ]
    }

    static def buildNodes(def root, List<Node> nodeList)
    {
        nodeList.each { model ->
            root.node(id: model.id, label: model.name) {
                if (model instanceof SrcPkgNode)
                {
                    buildSrcPkgNode(it, model)
                }
                else if (model instanceof MaintainerNode)
                {
                    buildMaintainerNode(it, model)
                }
            }
        }
    }

    static def buildMaintainerNode(def root, MaintainerNode maintainerNode)
    {
        root.attvalues {
            attvalue(for:  NODE_ATTR_INTERNET_ADDRESS,   value:  maintainerNode.address)
            attvalue(for:  NODE_ATTR_TYPE,               value:  NODE_ATTR_TYPE_MAINTAINER)
        }
    }

    static def buildSrcPkgNode(def root, SrcPkgNode srcPkgNode)
    {
        root.attvalues {
            attvalue(for:  NODE_ATTR_CHECKSUMS_SHA1,     value:  srcPkgNode.checksumsSha1)
            attvalue(for:  NODE_ATTR_CHECKSUMS_SHA256,   value:  srcPkgNode.checksumsSha256)
            attvalue(for:  NODE_ATTR_DM_UPLOAD_ALLOWED,  value:  srcPkgNode.dmUploadAllowed)
            attvalue(for:  NODE_ATTR_FILES,              value:  srcPkgNode.files)
            attvalue(for:  NODE_ATTR_FORMAT,             value:  srcPkgNode.format)
            attvalue(for:  NODE_ATTR_STANDARDS_VERSION,  value:  srcPkgNode.standardsVersion)
            if (srcPkgNode.vcsBrowser)
            {
                attvalue(for:  NODE_ATTR_VCS_BROWSER,        value:  srcPkgNode.vcsBrowser)
            }
            if (srcPkgNode.architecture)
            {
                attvalue(for:  NODE_ATTR_ARCHITECTURE,       value:  srcPkgNode.architecture)
            }
            if (srcPkgNode.homepage)
            {
                attvalue(for:  NODE_ATTR_HOMEPAGE,           value:  srcPkgNode.homepage)
            }
            if (srcPkgNode.homepage)
            {
                attvalue(for:  NODE_ATTR_PRIORITY,           value:  srcPkgNode.priority)
            }
            if (srcPkgNode.homepage)
            {
                attvalue(for:  NODE_ATTR_SECTION,            value:  srcPkgNode.section)
            }
            attvalue(for:  NODE_ATTR_VERSION,            value:  srcPkgNode.version)
            attvalue(for:  NODE_ATTR_TYPE,               value:  NODE_ATTR_TYPE_SOURCE_PACKAGE)
        }
        root.parents {
            parent(for:srcPkgNode.maintainer.id)
            srcPkgNode.uploaders.each { uploader ->
                parent(for:uploader.id)
            }
        }
    }
}