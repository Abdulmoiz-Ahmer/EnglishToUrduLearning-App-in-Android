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

public class Colors extends Fragment {
    ArrayList<DataModelClass> numbers;
    MediaPlayer mediaPlayer;
    AudioManager audioManager;
    AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.colors, container, false);
        ListView listView = view.findViewById(R.id.Items);
        CustomAdapter customAdapter = new CustomAdapter(this.getActivity(), getColors(), true);
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

    private ArrayList<DataModelClass> getColors() {
        numbers = new ArrayList<>();

        numbers.add(new DataModelClass(R.drawable.red, "Red", "لل",R.raw.red));
        numbers.add(new DataModelClass(R.drawable.green, "Green", "سبز",R.raw.green));
        numbers.add(new DataModelClass(R.drawable.blue, "Blue", "نیل",R.raw.blue));
        numbers.add(new DataModelClass(R.drawable.orange, "Orange", "نارنجی",R.raw.orange));
        numbers.add(new DataModelClass(R.drawable.black, "Black", "سیاہ",R.raw.black));
        numbers.add(new DataModelClass(R.drawable.yellow, "Yellow", "پیلا",R.raw.yellow));
        numbers.add(new DataModelClass(R.drawable.purple, "Purple", "جامنی",R.raw.purple));
        numbers.add(new DataModelClass(R.drawable.brown, "Brown", "بهورا",R.raw.brown));

        return numbers;
    }

    @Override
    public String toString() {
        return "Color";
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
