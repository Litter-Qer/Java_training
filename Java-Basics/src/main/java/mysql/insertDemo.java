package mysql;

import mapper.TestMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class insertDemo {
    public static void main(String[] args) throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession sqlSession = sqlSessionFactory.openSession();

        TestMapper testMapper = sqlSession.getMapper(TestMapper.class);

        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 1000; j++) {
                testMapper.add();
//                testMapper.read();
            }
            sqlSession.commit();
        }
        System.out.println("Done");

        sqlSession.close();
    }
}
