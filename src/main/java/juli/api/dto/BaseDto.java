package juli.api.dto;

import org.modelmapper.ModelMapper;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

public abstract class BaseDto<TEntity, TDto> implements Serializable {

    private Class<TDto> typeOfTDto;
    private Class<TEntity> typeOfTEntity;
    private String id;

    @SuppressWarnings("unchecked")
    public BaseDto() {
        this.typeOfTEntity = (Class<TEntity>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        this.typeOfTDto = (Class<TDto>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TDto mapFrom(TEntity entity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(entity, typeOfTDto);
    }

    public TEntity mapTo() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, typeOfTEntity);
    }
}
