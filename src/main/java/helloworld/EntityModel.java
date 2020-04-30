package helloworld;

import com.google.gson.Gson;
import helloworld.entities.BaseEntity;
import helloworld.services.BaseService;
import helloworld.services.ServiceRegistry;
import org.apache.wicket.model.LoadableDetachableModel;

public class EntityModel<T extends BaseEntity, S extends BaseService<T>> extends LoadableDetachableModel<T> {

    private Long id;

    private Class<T> entityClass;

    private Class<S> serviceClass;

    private transient T entity;

    private String newObjectSerialized;

    public EntityModel(T object, Class<S> serviceClass) {
        this.serviceClass = serviceClass;
        setObject(object);
    }

    public EntityModel(Class<S> serviceClass) {
        this.serviceClass = serviceClass;
    }

    @Override
    public void setObject(T object) {
        super.setObject(object);
        this.entity = object;
        this.entityClass = (Class<T>) object.getClass();
        final Long id = this.entity.getId();
        if (id != null) {
            this.id = id;
        }
    }

    @Override
    protected T load() {
        if (newObjectSerialized != null) {
            return new Gson().fromJson(newObjectSerialized, entityClass);
        }
        entity = ServiceRegistry.get(serviceClass).get(id);
        return entity;
    }

    @Override
    protected void onDetach() {
        super.onDetach();
        if (this.entity == null) {
            return;
        }
        if (entity.getId() == null) {
            newObjectSerialized = new Gson().toJson(entity);
        }
        entity = null;
    }
}
