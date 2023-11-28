package use_case.GeniusAuth;

import org.json.simple.parser.ParseException;

import java.io.IOException;

public interface GeniusInputBoundary {

    void execute() throws IOException, ParseException;

}
