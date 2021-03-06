package gg.octave.bot.commands.music.search

import com.jagrosh.jdautilities.selector
import gg.octave.bot.Launcher
import gg.octave.bot.commands.music.embedTitle
import gg.octave.bot.commands.music.embedUri
import gg.octave.bot.music.MusicLimitException
import gg.octave.bot.utils.Utils
import gg.octave.bot.utils.extensions.data
import gg.octave.bot.utils.extensions.selfMember
import gg.octave.bot.utils.extensions.voiceChannel
import me.devoxin.flight.api.Context
import me.devoxin.flight.api.annotations.Command
import me.devoxin.flight.api.annotations.Greedy
import me.devoxin.flight.api.entities.Cog
import java.awt.Color

class Soundcloud : Cog {
    @Command(aliases = ["sc"], description = "Search and see SoundCloud results.")
    fun soundcloud(ctx: Context, @Greedy query: String) {
        Launcher.players.get(ctx.guild).search("scsearch:$query", 5) { results ->
            if (results.isEmpty()) {
                return@search ctx.send("No search results for `$query`.")
            }

            val botChannel = ctx.selfMember!!.voiceState?.channel
            val userChannel = ctx.voiceChannel

            if (userChannel == null || botChannel != null && botChannel != userChannel) {
                return@search ctx.send {
                    setColor(Color(141, 20, 0))
                    setAuthor("SoundCloud Results", "https://soundcloud.com", "https://soundcloud.com/favicon.ico")
                    setThumbnail("https://octave.gg/assets/img/soundcloud.png")
                    setDescription(
                        results.joinToString("\n") {
                            "**[${it.info.embedTitle}](${it.info.embedUri})**\n" +
                                "**`${Utils.getTimestamp(it.duration)}`** by **${it.info.author}**\n"
                        }
                    )
                    setFooter("Want to play one of these music tracks? Join a voice channel and reenter this command.", null)
                }
            }

            Launcher.eventWaiter.selector {
                setColor(Color(255, 110, 0))
                setTitle("SoundCloud Results")
                setDescription("Select one of the following options to play them in your current music channel.")
                setUser(ctx.author)

                for (result in results) {
                    addOption("`${Utils.getTimestamp(result.info.length)}` **[${result.info.embedTitle}](${result.info.embedUri})**") {
                        if (ctx.member!!.voiceState!!.inVoiceChannel()) {
                            val manager = try {
                                Launcher.players.get(ctx.guild)
                            } catch (e: MusicLimitException) {
                                return@addOption e.sendToContext(ctx)
                            }

                            val args = query.split(" +".toRegex())
                            Play.smartPlay(ctx, manager, args, true, result.info.uri)
                        } else {
                            ctx.send("You're not in a voice channel anymore!")
                        }
                    }
                }
            }.display(ctx.textChannel!!)
        }
    }
}
