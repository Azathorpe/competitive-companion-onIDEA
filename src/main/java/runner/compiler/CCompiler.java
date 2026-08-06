package runner.compiler;

import com.azathorpe.cci.model.TestCase;
import runner.Message;

public class CCompiler implements Compiler {
    @Override
    public String compile(String sourceFile) {
        return "";
    }

    @Override
    public Message execute(String sourceFile, TestCase testCases) {
        return null;
    }
}
