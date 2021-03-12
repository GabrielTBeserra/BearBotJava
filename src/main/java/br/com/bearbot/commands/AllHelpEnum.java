package br.com.bearbot.commands;

public enum AllHelpEnum {

    Help(".help", "To say all commands", ""), Play(".play <Music>", "To play any music", ""),
    Pause(".pause", "To pause the music", ""), Unpause(".unpause", "To unpause the music", ".resume"),
    Stop(".stop", "To stop the music", ""), Skip(".skip", "To skip the playlist music", ""),
    Invite(".invite", "To get invite link from the bot", ""),
    Share(".sharescreen", "To share screen in the server", ""), Rank(".rank", "To see your rank in the server", ""),
    Shuffle(".shuffle", "To shuffle the playlist", ""), Queue(".queue", "To see all musics from playlist", ".q"),
    Info(".info", "To see bot info`s", "");

    private String command;
    private String message;
    private String alias;

    AllHelpEnum(String command, String message, String alias) {
        this.command = command;
        this.message = message;
        this.alias = alias;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

}