package helloworld.resources;

import org.apache.wicket.request.resource.PackageResourceReference;

public class CafeOneTheme extends PackageResourceReference {

    private static final CafeOneTheme INSTANCE = new CafeOneTheme();

    public CafeOneTheme() {
        super(CafeOneTheme.class, "cafeone.css");
    }

    public static CafeOneTheme get() {
        return INSTANCE;
    }

}