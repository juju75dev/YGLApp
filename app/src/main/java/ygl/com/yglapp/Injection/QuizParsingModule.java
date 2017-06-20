package ygl.com.yglapp.Injection;

import dagger.Module;
import dagger.Provides;

/**
 * Created by juju on 16/06/2017.
 */

@Module
public class QuizParsingModule {

    @Provides
    public QuizParser provideQuizParser(){

        return new QuizParser();

    };


}
