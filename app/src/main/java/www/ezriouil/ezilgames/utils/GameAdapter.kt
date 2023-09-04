package www.ezriouil.ezilgames.utils

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import www.ezriouil.ezilgames.R
import www.ezriouil.ezilgames.remote.Game

class GameAdapter(private val listener: Listener) :
    RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    private lateinit var games: List<Game>

    override fun getItemCount() = games.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.game_item, parent, false)
        return GameViewHolder(view)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val itemData = games[holder.adapterPosition]

        Log.d("TAG", itemData.gameBg.toString())

        holder.gameTitle.text = itemData.gameTitle?.uppercase()

        Picasso.get().load(itemData.gameBg).apply {
            placeholder(R.drawable.placeholder)
            into(holder.gameBg)
            fit()
            error(R.drawable.error)
        }

        holder.gameCard.setOnClickListener {
            listener.onCardClick(
                url = itemData.gameUrl,
                screenMode = itemData.screenMode
            )
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setGames(newGames: List<Game>) {
        games = newGames
        this.notifyDataSetChanged()
    }

    class GameViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val gameBg: ImageView = view.findViewById(R.id.gameBg)
        val gameTitle: TextView = view.findViewById(R.id.gameTitle)
        val gameCard: CardView = view.findViewById(R.id.gameCard)

    }
}