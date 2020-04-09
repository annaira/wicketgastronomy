package helloworld;

import helloworld.entities.BaseEntity;
import helloworld.services.BaseService;
import helloworld.services.ServiceRegistry;
import org.apache.wicket.model.LoadableDetachableModel;

public class EntityModel<T extends BaseEntity, S extends BaseService<T>> extends LoadableDetachableModel<T> {

    private final Long id;
    private final Class<S> serviceClass;

    public EntityModel(Long id, Class<S> serviceClass) {
        this.id = id;
        this.serviceClass = serviceClass;
    }

    @Override
    protected T load() {
        return ServiceRegistry.get(serviceClass).get(id);
    }
}
