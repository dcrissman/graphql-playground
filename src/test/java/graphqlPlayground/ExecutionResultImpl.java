package graphqlPlayground;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import graphql.ExecutionResult;
import graphql.GraphQLError;

public class ExecutionResultImpl<T> implements ExecutionResult {

    private T data;

    @JsonDeserialize(contentAs = GraphQLErrorImpl.class)
    private List<GraphQLError> errors;

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public T getData() {
        return data;
    }

    @Override
    public List<GraphQLError> getErrors() {
        return errors;
    }

    public void setErrors(List<GraphQLError> errors) {
        this.errors = errors;
    }

    @Override
    public Map<Object, Object> getExtensions() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> toSpecification() {
        // TODO Auto-generated method stub
        return null;
    }

}
