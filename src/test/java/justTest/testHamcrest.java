package justTest;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
//import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.core.IsIterableContaining.hasItems;
import static org.junit.matchers.JUnitMatchers.*;


public class testHamcrest {
    @Test
    public void test1(){//equalTo断言
        System.out.println("11111111");
        assertThat("手机号码填写错误2",equalTo("手机号码填写错误"));
    }

    @Test
    public void test2(){//hasItems断言
        ArrayList<String> arrayList=new ArrayList<String>();
        arrayList.add("a");
        arrayList.add("b");
        arrayList.add("c");
        assertThat(arrayList,hasItems("a","c"));
        System.out.println(arrayList.get(0)+","+arrayList.get(1));
    }

    @Test
    public void test3(){//循环try catch
        List<Integer> list=new ArrayList<Integer>();
        list.add(0);
        try{
            list.get(0);
            list.get(1);
            System.out.println("try内部错误");
        }catch (Exception e){
            try{
                list.get(2);
            }catch (Exception e1){

                System.out.println("进入catch2");
            }

            System.out.println("进入catch");
        }
    }
}
