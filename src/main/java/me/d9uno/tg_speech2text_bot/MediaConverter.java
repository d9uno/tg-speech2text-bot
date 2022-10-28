package me.d9uno.tg_speech2text_bot;

import java.io.File;

import ws.schild.jave.Encoder;

public class MediaConverter {
    private final Encoder encoder = new Encoder();

    private File changeFileExtension(File sourceFile, String newExtension) {
        var index = sourceFile.getPath().lastIndexOf(".") + 1;
        return new File(sourceFile.getPath().substring(0, index) + newExtension);
    }
}
