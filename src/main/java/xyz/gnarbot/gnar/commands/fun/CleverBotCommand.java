package xyz.gnarbot.gnar.commands.fun;

import com.google.code.chatterbotapi.ChatterBot;
import com.google.code.chatterbotapi.ChatterBotFactory;
import com.google.code.chatterbotapi.ChatterBotSession;
import com.google.inject.Inject;
import xyz.gnarbot.gnar.handlers.commands.Command;
import xyz.gnarbot.gnar.handlers.commands.CommandExecutor;
import xyz.gnarbot.gnar.handlers.servers.Host;
import xyz.gnarbot.gnar.utils.Note;

@Command(aliases = "cbot")
public class CleverBotCommand extends CommandExecutor
{
    
    @Inject
    public Host host;
    
    private ChatterBotFactory factory = new ChatterBotFactory();
    
    private ChatterBot bot = null;
    
    private ChatterBotSession session = null;
    
    @Override
    public void execute(Note note, String label, String[] args)
    {
        note.reply("Command is being worked on :), use _pbot for a bit");
        
        /*
        try
        {
            if (bot == null)
            {
                bot = factory.create(ChatterBotType.CLEVERBOT);
                session = bot.createSession();
                msg.reply("Clever-Bot session created for the server.");
            }

            String input = StringUtils.join(args, " ");

            String output = session.think(input);
            msg.replyRaw("**[CleverBot]** ─ `" + output + "`");
        }
        catch (Exception e)
        {
            msg.reply("CleverBot has encountered an exception. Resetting CleverBot.");
            bot = null;
        }*/
    }
    
}