import it.cr.CollectionRunnerApplication;
import it.cr.collection.CollectionRunner;
import it.cr.example.dto.SecondRequest;
import it.cr.example.dto.FirstRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = CollectionRunnerApplication.class)
class CollectionRunnerTest {

    @Autowired
    CollectionRunner cr;
    @Test
    void testCR() throws Exception {

        cr.getContext().put("X-CSRF-TOKEN","aaa-bbb-ccc-ddd");
        cr.getContext().put("Cookie","JSESSIONID=eeeeee");

        cr.chain((new FirstRequest()));
        cr.chain(new SecondRequest());

        cr.run(10L);
    }
}
