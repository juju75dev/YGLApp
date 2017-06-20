package ygl.com.yglapp.Injection;

import dagger.Component;
import ygl.com.yglapp.Activity.MainActivity;

/**
 * Created by juju on 20/06/2017.
 */
@Component(modules = {QuizParsingModule.class,QuizAdapterModule.class})
public interface QuizParsingComponent {

    void inject(MainActivity main);

}
