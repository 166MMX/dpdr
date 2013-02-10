package name.harth.dpdr.io.gexf

import groovy.xml.XmlUtil
import name.harth.dpdr.model.gexf.MaintainerNode
import name.harth.dpdr.model.gexf.Node
import groovy.xml.StreamingMarkupBuilder
import name.harth.dpdr.model.gexf.SrcPkgNode

class GexfWriter
{
    public static final int NODE_ATTR_INTERNET_ADDRESS = 0
    public static final int ATTR_CHECKSUMS_SHA1 = 1
    public static final int ATTR_CHECKSUMS_SHA256 = 2
    public static final int ATTR_DM_UPLOAD_ALLOWED = 3
    public static final int ATTR_FILES = 4
    public static final int ATTR_FORMAT = 5
    public static final int ATTR_STANDARDS_VERSION = 6
    public static final int ATTR_VCS_BROWSER = 7
    public static final int ATTR_ARCHITECTURE = 8
    public static final int ATTR_HOMEPAGE = 9
    public static final int ATTR_PRIORITY = 10
    public static final int ATTR_SECTION = 11
    public static final int ATTR_VERSION = 12
    public static final int ATTR_TYPE = 13
    public static final int EDGE_ATTR_MAINTAINER = 0
    public static final int EDGE_ATTR_UPLOADER = 1

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
                        attribute(id:  ATTR_CHECKSUMS_SHA1,     type:  'boolean',     title:  'SHA1 checksums')
                        attribute(id:  ATTR_CHECKSUMS_SHA256,   type:  'boolean',     title:  'SHA256 checksums')
                        attribute(id:  ATTR_DM_UPLOAD_ALLOWED,  type:  'boolean',     title:  'Debian maintainers upload allowed')
                        attribute(id:  ATTR_FILES,              type:  'integer',     title:  'Files')
                        attribute(id:  ATTR_FORMAT,             type:  'liststring',  title:  'Source package format') {
                            options {
                                mkp.yield '1.0|3.0 (native)|(quilt)'
                            }
                        }
                        attribute(id:  ATTR_STANDARDS_VERSION,  type:  'string',      title:  'Standards version')
                        attribute(id:  ATTR_VCS_BROWSER,        type:  'string',      title:  'VCS web interface')
                        attribute(id:  ATTR_ARCHITECTURE,       type:  'string',      title:  'Architecture')
                        attribute(id:  ATTR_HOMEPAGE,           type:  'string',      title:  'Homepage')
                        attribute(id:  ATTR_PRIORITY,           type:  'liststring',  title:  'Priority') {
                            options {
                                mkp.yield 'required|important|standard|optional|extra'
                            }
                        }
                        attribute(id:  ATTR_SECTION,            type:  'string',      title:  'Section')
                        attribute(id:  ATTR_VERSION,            type:  'string',      title:  'Version')
                        attribute(id:  ATTR_TYPE,               type:  'liststring',  title:  'Node type') {
                            options {
                                mkp.yield 'maintainer|source|binary'
                            }
                        }
                    }
                    attributes(class:'edge', mode:'static') {
                        attribute(id:  EDGE_ATTR_MAINTAINER,    type:  'boolean',  title:  'Maintainer') {
                            'default' {
                                mkp.yield 'false'
                            }
                        }
                        attribute(id:  EDGE_ATTR_UPLOADER,    type:  'boolean',  title:  'Uploader') {
                            'default' {
                                mkp.yield 'false'
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
                        attvalue(for:  EDGE_ATTR_MAINTAINER,     value:  'true')
                    }
                }
                model.uploaders.each { uploader ->
                    root.edge(id: counter++, source: uploader.id, target: model.id) {
                        attvalues {
                            attvalue(for:  EDGE_ATTR_UPLOADER,     value:  'true')
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
            attvalue(for:  ATTR_TYPE,               value:  'maintainer')
        }
    }

    static def buildSrcPkgNode(def root, SrcPkgNode srcPkgNode)
    {
        root.attvalues {
            attvalue(for:  ATTR_CHECKSUMS_SHA1,     value:  srcPkgNode.checksumsSha1)
            attvalue(for:  ATTR_CHECKSUMS_SHA256,   value:  srcPkgNode.checksumsSha256)
            attvalue(for:  ATTR_DM_UPLOAD_ALLOWED,  value:  srcPkgNode.dmUploadAllowed)
            attvalue(for:  ATTR_FILES,              value:  srcPkgNode.files)
            attvalue(for:  ATTR_FORMAT,             value:  srcPkgNode.format)
            attvalue(for:  ATTR_STANDARDS_VERSION,  value:  srcPkgNode.standardsVersion)
            if (srcPkgNode.vcsBrowser)
            {
                attvalue(for:  ATTR_VCS_BROWSER,        value:  srcPkgNode.vcsBrowser)
            }
            if (srcPkgNode.architecture)
            {
                attvalue(for:  ATTR_ARCHITECTURE,       value:  srcPkgNode.architecture)
            }
            if (srcPkgNode.homepage)
            {
                attvalue(for:  ATTR_HOMEPAGE,           value:  srcPkgNode.homepage)
            }
            if (srcPkgNode.homepage)
            {
                attvalue(for:  ATTR_PRIORITY,           value:  srcPkgNode.priority)
            }
            if (srcPkgNode.homepage)
            {
                attvalue(for:  ATTR_SECTION,            value:  srcPkgNode.section)
            }
            attvalue(for:  ATTR_VERSION,            value:  srcPkgNode.version)
            attvalue(for:  ATTR_TYPE,               value:  'source')
        }
        root.parents {
            parent(for:srcPkgNode.maintainer.id)
            srcPkgNode.uploaders.each { uploader ->
                parent(for:uploader.id)
            }
        }
    }
}