package com.example.virtualreadingroom;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private Context context;
    private List<Book> bookList;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Book book);
    }

    public BookAdapter(Context context, List<Book> bookList, OnItemClickListener listener) {
        this.context = context;
        this.bookList = bookList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.tvTitle.setText(book.getName());
        holder.tvAuthor.setText("Written by: " + book.getAuthor());
        holder.tvGenre.setText("Genre: " + book.getGenre());

        holder.ivCover.setImageURI(Uri.parse(book.getCoverPath()));

        holder.itemView.setOnClickListener(v -> listener.onItemClick(book));
        holder.tvRating.setText(String.format("Rating: %.1f", book.getAverageRating())); // new

        holder.btnRateUs.setOnClickListener(v -> {
            Intent intent = new Intent(context, RateUsActivity.class);
            intent.putExtra("bookName", book.getName());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvAuthor, tvGenre, tvRating;
        ImageView ivCover;
        Button btnRateUs;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvBookName);
            tvAuthor = itemView.findViewById(R.id.tvAuthorName);
            tvGenre = itemView.findViewById(R.id.tvGenreName);
            ivCover = itemView.findViewById(R.id.ivCover);
            btnRateUs = itemView.findViewById(R.id.btnRateUs);
            tvRating = itemView.findViewById(R.id.tvRating); // new
        }
    }

}
