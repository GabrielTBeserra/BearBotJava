package br.com.bearbot.utils;

import br.com.bearbot.DAO.GetPositionRank;
import br.com.bearbot.DAO.NumberOfMessagesDAO;
import br.com.bearbot.commands.RankEnum;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class CriarImagem {

    /**
     * Gera imagem do rank
     *
     * @param event
     * @return
     * @throws IOException
     */

    public File getImage(MessageReceivedEvent event) throws IOException {
        int width = 340;
        int height = 500;
        long idUser = event.getAuthor().getIdLong();
        long idServer = event.getGuild().getIdLong();
        int pos;

        GetPositionRank position = new GetPositionRank();

        pos = position.getList(event.getGuild().getId()).indexOf(event.getAuthor().getId()) + 1;

        // Pega a quantidade de xp do author da mensagem
        NumberOfMessagesDAO qtns = new NumberOfMessagesDAO();

        long msgConuter = qtns.QntMessages(idUser, idServer);
        long xpAmount = msgConuter * 10;

        List<Long> rankList = new ArrayList<Long>();

        // Coloca todos os xps dos levels em uma lista
        for (RankEnum ranks : RankEnum.values()) {
            rankList.add(ranks.getXp());
        }

        int i = 0;

        // compara o indice com o valor da lista de xps
        for (RankEnum ranks : RankEnum.values()) {
            if (xpAmount > ranks.getXp()) {
                i++;
            }
        }

        String nextRank = "";
        int levelBar = 0;
        if (rankList.size() <= i) {

            nextRank = "MAX";
            levelBar = 260;
        } else {
            nextRank = "" + rankList.get(i);

            long valorDoProximoRank;
            long porcentagemEntreSeuLevelEOProximo;
            long porcentagemEntreOComecoDaBarraEOMaximo;
            long valorFinalParaConversar;

            valorDoProximoRank = rankList.get(i);

            if (valorDoProximoRank == 0) {
                valorDoProximoRank = 1000;
            }

            long valorAnterior = rankList.get(i - 1);
            long xpMenosAnterior = xpAmount - valorAnterior;
            long maximoMenosAnterios = valorDoProximoRank - valorAnterior;

            porcentagemEntreSeuLevelEOProximo = (xpMenosAnterior * 100) / maximoMenosAnterios;

            porcentagemEntreOComecoDaBarraEOMaximo = (porcentagemEntreSeuLevelEOProximo * 100) / 220;

            valorFinalParaConversar = (porcentagemEntreOComecoDaBarraEOMaximo * 220) / 100;

            levelBar = (int) map(valorFinalParaConversar, 0, 100, 40, 260);

        }

        // Cria a imagem com as dimensoes
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = bufferedImage.createGraphics();

        URL url = new URL(event.getAuthor().getAvatarUrl());

        URLConnection connection = url.openConnection();
        connection.setRequestProperty("User-Agent", "xxxxxx");

        BufferedImage image = ImageIO.read(connection.getInputStream());

        // Dimensoes da imagem e fundo com a cor
        g2d.setColor(new Color(36, 36, 36));
        g2d.fillRect(0, 0, width, height);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Barra de xp do background
        g2d.setColor(new Color(48, 48, 48));
        g2d.fillRoundRect(40, 450, 260, 20, 20, 20);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Barra dinamica de level
        g2d.setColor(new Color(106, 0, 194));
        g2d.fillRoundRect(40, 450, levelBar, 20, 20, 20);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Faz a medicao do string do nome do author
        FontMetrics fm = g2d.getFontMetrics();
        int x = fm.stringWidth(event.getAuthor().getAsTag());

        // Desenha o nome do author na tela
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("TimesRoman", Font.PLAIN, (int) map(x, 25, 150, 40, 20)));
        drawCenteredString(event.getAuthor().getAsTag(), width, height, g2d);

        // Desenha a pasicao
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("TimesRoman", Font.PLAIN, 35));
        drawCenteredString("#" + pos, width, height + 160, g2d);

        // Desenha a quantidade de XP na imagem
        g2d.setColor(Color.white);
        g2d.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        drawCenteredString(xpAmount + " / " + nextRank, width, height + 365, g2d);

        // Desenha XP na imagem
        g2d.setColor(Color.white);
        g2d.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        drawCenteredString("XP", width, height + 320, g2d);

        // Desenha o avatar redondo, na imagem
        g2d.setClip(new Ellipse2D.Float(80, 10, 190, 190));
        g2d.drawImage(image, 80, 10, 190, 190, null);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.dispose();

        File file = new File("rank.png");
        ImageIO.write(bufferedImage, "png", file);

        return file;
    }

    // Mapeia dois intervalos
    private long map(long x, long in_min, long in_max, long out_min, long out_max) {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }

    private void drawCenteredString(String s, int w, int h, Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        int x = (w - fm.stringWidth(s)) / 2;
        int y = (fm.getAscent() + (h - (fm.getAscent() + fm.getDescent())) / 2);
        g.drawString(s, x, y);
    }
}
