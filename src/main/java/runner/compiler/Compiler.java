package runner.compiler;

import com.azathorpe.cci.model.TestCase;
import runner.Message;

import java.util.Objects;

public interface Compiler {
    String NO_ERR = "NO_ERR";
    /**
     * 编译文件
     * @param sourceFile 源文件
     */
    String compile(String sourceFile);

    Message execute(String sourceFile, TestCase testCases);

    default Message start(String sourceFile, TestCase testCases){
        String compile = compile(sourceFile);
        if (!Objects.equals(compile, NO_ERR))
            return new Message("", compile);

        Message execute = execute(sourceFile, testCases);
        return judge(testCases, execute);
    };

    default Message judge(TestCase testCase, Message output){
        output.setStatus(testCase.getOutput().trim().equals(output.getOutput().trim()));
        return output;
    };
}
