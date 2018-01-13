package graphqlPlayground;

import java.util.List;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

public class GraphQLErrorImpl implements GraphQLError {

    private String message;
    private List<SourceLocation> locations;
    private ErrorType errorType;

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setLocations(List<SourceLocation> locations) {
        this.locations = locations;
    }

    @Override
    public List<SourceLocation> getLocations() {
        return locations;
    }

    public void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }

    @Override
    public ErrorType getErrorType() {
        return errorType;
    }

}
