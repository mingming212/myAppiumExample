package justTest;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class testJunit5_CsvSource {
    @ParameterizedTest
    @CsvSource({
            "18001367612,111111,手机号码填写错误",
            "1800136761,dddddd,手机号码填写错误"
    })
    public void test1(String name,String password,String message){
        System.out.println(""+name+",  "+password+", "+message);
    }
}
