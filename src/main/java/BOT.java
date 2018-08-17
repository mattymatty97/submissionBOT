import com.submissionBOT.*;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class BOT
{
    static String url;
    public static void main(String[] arguments) throws Exception
    {

        JDA api = new JDABuilder(AccountType.BOT).setToken(System.getenv("BOT_TOKEN")).buildAsync();

        ListenerAdapter ls = new MyListener();
        api.addEventListener(ls);
        api.getPresence().setGame(Game.watching("for submissions  ||  v 0.3") );

        //what the bot should do before shutting down
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            api.shutdownNow();
            ((MyListener) ls).shutdown();
        }));
    }


}
