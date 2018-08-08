import com.submissionBOT.*;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

import net.dv8tion.jda.core.entities.Game;
public class BOT
{
    static String url;
    public static void main(String[] arguments) throws Exception
    {

        JDA api = new JDABuilder(AccountType.BOT).setToken(System.getenv("BOT_TOKEN")).buildAsync();

        api.addEventListener(new MyListener());
        api.getPresence().setGame(Game.playing("v0.1 submission prj"));
    }


}
