package com.example.learnxcompile;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.example.learnxcompile.Items.Language;
import com.example.learnxcompile.Models.ChapterModel;

import java.util.List;

public class ChaptersActivity extends AppCompatActivity {

    private ChapterModel chapterModel;
    private RecyclerView recyclerView;
    private String languageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapters);

        languageName = getIntent().getStringExtra("LANGUAGE_NAME");
        TextView tvLanguageTitle = findViewById(R.id.tvLanguageTitle);
        tvLanguageTitle.setText(languageName + " Chapters");

        chapterModel = new ChapterModel(this);
        recyclerView = findViewById(R.id.recyclerViewChapters);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadChapters();
    }

    private void loadChapters() {
        Language language = chapterModel.getLanguageByName(languageName);
        if (language != null && language.chapters != null) {
            recyclerView.setAdapter(new ChaptersAdapter(language.chapters));
        } else {
            Toast.makeText(this, "No chapters found for " + languageName, Toast.LENGTH_SHORT).show();
        }
    }

    private class ChaptersAdapter extends RecyclerView.Adapter<ChaptersAdapter.ViewHolder> {
        private List<Language.Chapter> chapters;

        public ChaptersAdapter(List<Language.Chapter> chapters) {
            this.chapters = chapters;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chapter, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Language.Chapter chapter = chapters.get(position);
            holder.tvNumber.setText(String.valueOf(position + 1));
            holder.tvTitle.setText(chapter.title);
            holder.tvDescription.setText(chapter.description);

            if (chapter.isLocked) {
                holder.ivLock.setImageResource(R.drawable.ic_locked);
                holder.itemView.setAlpha(0.6f);
                holder.itemView.setOnClickListener(v -> 
                    Toast.makeText(ChaptersActivity.this, "Chapter Locked", Toast.LENGTH_SHORT).show()
                );
            } else {
                holder.ivLock.setImageResource(R.drawable.ic_unlocked);
                holder.itemView.setAlpha(1.0f);
                holder.itemView.setOnClickListener(v -> 
                    Toast.makeText(ChaptersActivity.this, "Opening " + chapter.title, Toast.LENGTH_SHORT).show()
                );
            }
        }

        @Override
        public int getItemCount() {
            return chapters.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvNumber, tvTitle, tvDescription;
            ImageView ivLock;

            ViewHolder(View itemView) {
                super(itemView);
                tvNumber = itemView.findViewById(R.id.tvChapterNumber);
                tvTitle = itemView.findViewById(R.id.tvChapterTitle);
                tvDescription = itemView.findViewById(R.id.tvChapterDescription);
                ivLock = itemView.findViewById(R.id.ivLock);
            }
        }
    }
}
