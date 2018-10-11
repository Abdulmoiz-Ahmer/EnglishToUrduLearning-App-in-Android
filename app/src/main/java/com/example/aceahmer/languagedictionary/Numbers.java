package com.example.aceahmer.languagedictionary;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class Numbers extends Fragment{
    ArrayList<DataModelClass> numbers;
    MediaPlayer mediaPlayer;
    AudioManager audioManager;
    AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.numbers, container, false);
        ListView listView = view.findViewById(R.id.Items);
        CustomAdapter customAdapter = new CustomAdapter(this.getActivity(), getNumbers(), true);
        listView.setAdapter(customAdapter);
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        mediaPlayer = MediaPlayer.create(getActivity(), R.raw.aunt);
        onAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int i) {
                if (i == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || i == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                    mediaPlayer.pause();
                    mediaPlayer.seekTo(0);
                } else if (i == AudioManager.AUDIOFOCUS_LOSS) {
                    releaseMediaPlayer();
                } else if (i == AudioManager.AUDIOFOCUS_GAIN) {
                    mediaPlayer.start();
                }

            }
        };
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if ((audioManager.requestAudioFocus(onAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT) == AudioManager.AUDIOFOCUS_REQUEST_GRANTED)) {
                    mediaPlayer = MediaPlayer.create(getActivity(), numbers.get(i).getAudioFile());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            releaseMediaPlayer();
                        }
                    });

                }


            }
        });


        return view;
    }

    private ArrayList<DataModelClass> getNumbers() {
        numbers=new ArrayList<>();
        numbers.add(new DataModelClass(R.drawable.if_number_0_green_1553051,"Zero","صفر",R.raw.zero));
        numbers.add(new DataModelClass(R.drawable.if_number_1_green_1553102,"One","ایک",R.raw.one));
        numbers.add(new DataModelClass(R.drawable.if_number_2_green_1553086,"Two","دو",R.raw.two));
        numbers.add(new DataModelClass(R.drawable.if_number_3_green_1553078,"Three","تین",R.raw.three));
        numbers.add(new DataModelClass(R.drawable.if_number_4_green_1553044,"Four","چار",R.raw.four));
        numbers.add(new DataModelClass(R.drawable.if_number_5_green_1553087,"Five","پانچ",R.raw.five));
        numbers.add(new DataModelClass(R.drawable.if_number_6_green_1553092,"Six","چھ",R.raw.six));
        numbers.add(new DataModelClass(R.drawable.if_number_7_green_1553061,"Seven","سات",R.raw.seven));
        numbers.add(new DataModelClass(R.drawable.if_number_8_green_1553093,"Eight","آٹھ",R.raw.eight));
        numbers.add(new DataModelClass(R.drawable.if_number_9_green_1553080,"Nine","نو",R.raw.nine));


        return numbers;
    }

    @Override
    public String toString() {
        return "No";
    }
    public void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
            audioManager.abandonAudioFocus(onAudioFocusChangeListener);

        }
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}
