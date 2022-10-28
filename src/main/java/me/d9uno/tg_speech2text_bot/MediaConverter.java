package me.d9uno.tg_speech2text_bot;

import java.io.File;

import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;

public class MediaConverter {
    private final Encoder encoder = new Encoder();

    public File convertAudio(File sourceFile, String format, String codec, int bitRate, int channels, int samplingRate) {
        var audioAttributes = new AudioAttributes()
            .setBitRate(bitRate)
            .setChannels(channels)
            .setCodec(codec)
            .setSamplingRate(samplingRate);
        
        var encodingAttributes = new EncodingAttributes()
            .setAudioAttributes(audioAttributes)
            .setOutputFormat(format);

        var targetFile = changeFileExtension(sourceFile, format);
        
        try { encoder.encode(new MultimediaObject(sourceFile), targetFile, encodingAttributes); }
        catch (IllegalArgumentException | EncoderException e) {
            e.printStackTrace();
        }

        return targetFile;
    }

    private File changeFileExtension(File sourceFile, String newExtension) {
        var index = sourceFile.getPath().lastIndexOf(".") + 1;
        return new File(sourceFile.getPath().substring(0, index) + newExtension);
    }
}
