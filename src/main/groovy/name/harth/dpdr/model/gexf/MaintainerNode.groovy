package name.harth.dpdr.model.gexf

import org.slf4j.LoggerFactory

import javax.mail.internet.AddressException
import javax.mail.internet.InternetAddress
import java.util.regex.MatchResult
import java.util.regex.Pattern

class MaintainerNode extends Node
{
    private static final logger = LoggerFactory.getLogger(MaintainerNode.class)
    private static final Pattern SIMPLE_INTERNET_ADDRESS = ~/["']?(.+)["']? \<(.+)\>/

    String address

    static Map<String, MaintainerNode> instances = new HashMap<String, MaintainerNode>()

    static MaintainerNode create(String maintainerAddress)
    {
        String key = maintainerAddress.normalize().trim().toLowerCase(Locale.ENGLISH)
        MaintainerNode result = instances.get(key)
        if (result == null)
        {
            result = createInstance(key, maintainerAddress)
        }
        return result
    }

    private static createInstance(String key, String maintainerAddress)
    {
        MaintainerNode result = new MaintainerNode()
        InternetAddress internetAddress = null
        try
        {
            internetAddress = new InternetAddress(maintainerAddress)
        }
        catch (AddressException ex)
        {
            logger.info("Strict parsing failed. Malformed Internet address '{}' detected; Retrying lax", maintainerAddress, ex)
        }
        if (internetAddress == null)
        {
            try
            {
                internetAddress = new InternetAddress(maintainerAddress, false)
            }
            catch (AddressException ex)
            {
                logger.info("Lax parsing failed. Malformed Internet address '{}' detected; Retrying with simple regular expression", maintainerAddress, ex)
            }
        }
        if (internetAddress == null)
        {
            MatchResult matches = maintainerAddress =~ SIMPLE_INTERNET_ADDRESS
            result.name    = matches[0][1]
            result.address = matches[0][2]
        }
        else
        {
            result.name    = internetAddress.personal
            result.address = internetAddress.address
        }

        instances.put(key, result)
        return result
    }
}
