package helloworld.resources;

import org.apache.wicket.request.resource.PackageResourceReference;

public class DefaultTheme extends PackageResourceReference {

    private static final DefaultTheme INSTANCE = new DefaultTheme();

    public DefaultTheme() {
        super(DefaultTheme.class, "default.css");
    }

    public static DefaultTheme get() {
        return INSTANCE;
    }

}