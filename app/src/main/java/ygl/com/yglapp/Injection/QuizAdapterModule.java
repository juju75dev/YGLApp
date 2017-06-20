package ygl.com.yglapp.Injection;

import dagger.Module;
import dagger.Provides;
import ygl.com.yglapp.Adapter.QuizzAdapter;

/**
 * Created by juju on 20/06/2017.
 */

@Module
public class QuizAdapterModule {

    @Provides
    public QuizzAdapter provideQuizAdapter(){

        return new QuizzAdapter();

    };

}
