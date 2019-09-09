package casteldao.testing;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class TestStoreTest {

    @Test
    public void initialQuery() {
        TestStore.initialQuery();
        assertThat(TestStore.authors.getAllCache().size()).isEqualTo(8);
        assertThat(TestStore.books.getAllCache().size()).isEqualTo(13);
    }

    @Test
    public void insertMockData() {
    }
}