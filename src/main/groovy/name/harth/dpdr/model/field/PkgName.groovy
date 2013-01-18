package name.harth.dpdr.model.field

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.mail.Header
import java.util.regex.Pattern

class PkgName implements PkgField
{
    private static final Logger logger = LoggerFactory.getLogger(PkgName.class)

    // <http://www.debian.org/doc/debian-policy/ch-controlfields.html#s-f-Source>
    static final Pattern regEx = ~/[a-z0-9][a-z0-9.+-]+/

    String name

    PkgName()
    {

    }

    PkgName(Header header)
    {
        name = header.value
    }

    PkgName(String value)
    {
        name = value
    }

    void setName(String value)
    {
        if (!isValid(value))
        {
            logger.warn('Invalid value for PkgName')
        }
        name = value
    }

    static boolean isValid(value)
    {
        value ==~ regEx
    }

    boolean isValid()
    {
        isValid(name)
    }
}