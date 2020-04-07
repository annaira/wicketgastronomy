package helloworld.resources;

import org.apache.wicket.request.resource.ContextRelativeResourceReference;

public class LogoResourceReference extends ContextRelativeResourceReference {

    public LogoResourceReference() {
        super("logo.png");
    }
}
