package com.gamestudio87.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;
public class FlappyBird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;

	Texture gameover;

	Texture[] birds;
	int flapState = 0;
	float birdY = 0;
	float velocity = 0;
	Circle birdCircle;
	int score = 0;
	int scoringTube = 0;
	BitmapFont font;

	int gameState = 0;
	float gravity = 1.5f;

	Texture topTube;
	Texture bottomTube;
	float gap = 450;
	float maxTubeOffset;
	Random randomGenerator;
	float tubeVelocity = 4;
	int numberOfTubes = 4;
	float[] tubeX = new float[numberOfTubes];
	float[] tubeOffset = new float[numberOfTubes];
	float distanceBetweenTubes;
	Rectangle[] topTubeRectangles;
	Rectangle[] bottomTubeRectangles;

	Texture playButton;
	Texture exitButton;
	Texture playText;
	Rectangle playButtonBounds;
	Rectangle exitButtonBounds;

	@Override
	public void create() {
		batch = new SpriteBatch();
		background = new Texture("background.png");
		gameover = new Texture("gameover.png");
		playButton = new Texture("img.png"); // Add your Play button image
		exitButton = new Texture("img_1.png"); // Add your Exit button image
		playText = new Texture("img_2.png");

		birdCircle = new Circle();
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(10);

		birds = new Texture[2];
		birds[0] = new Texture("flappybirdup.png");
		birds[1] = new Texture("flappybirddown.png");

		topTube = new Texture("toptube.png");
		bottomTube = new Texture("bottomtube.png");
		maxTubeOffset = Gdx.graphics.getHeight() / 2 - gap / 2 - 100;
		randomGenerator = new Random();
		distanceBetweenTubes = Gdx.graphics.getWidth() * 3 / 4;
		topTubeRectangles = new Rectangle[numberOfTubes];
		bottomTubeRectangles = new Rectangle[numberOfTubes];

		// Calculate gravity and velocity based on screen height
		float baseHeight = 1920f; // Example base height for scaling
		gravity = (1.4f / baseHeight) * Gdx.graphics.getHeight();
		velocity = (20f / baseHeight) * Gdx.graphics.getHeight();

		// Initialize button positions
		float buttonWidth = Gdx.graphics.getWidth() / 4f;
		float buttonHeight = Gdx.graphics.getHeight() / 10f;
		float centerX = Gdx.graphics.getWidth() / 2f;

		playButtonBounds = new Rectangle(centerX - buttonWidth - 20, Gdx.graphics.getHeight() / 4f, buttonWidth, buttonHeight);
		exitButtonBounds = new Rectangle(centerX + 20, Gdx.graphics.getHeight() / 4f, buttonWidth, buttonHeight);

		startGame();
	}

	public void startGame() {

		birdY = Gdx.graphics.getHeight() / 2 - birds[0].getHeight() / 2;

		for (int i = 0; i < numberOfTubes; i++) {

			tubeOffset[i] = (randomGenerator.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 200);

			tubeX[i] = Gdx.graphics.getWidth() / 2 - topTube.getWidth() / 2 + Gdx.graphics.getWidth() + i * distanceBetweenTubes;

			topTubeRectangles[i] = new Rectangle();
			bottomTubeRectangles[i] = new Rectangle();

		}

	}

	@Override
	public void render() {
		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		if (gameState == 1) { // Game running
			if (tubeX[scoringTube] < Gdx.graphics.getWidth() / 2) {
				score++;
				scoringTube = (scoringTube + 1) % numberOfTubes;
			}

			if (Gdx.input.justTouched()) {
				velocity = -(20/ 1920f) * Gdx.graphics.getHeight(); // Scale the upward velocity
			}


			for (int i = 0; i < numberOfTubes; i++) {
				if (tubeX[i] < -topTube.getWidth()) {
					tubeX[i] += numberOfTubes * distanceBetweenTubes;
					tubeOffset[i] = (randomGenerator.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 200);
				} else {
					tubeX[i] -= tubeVelocity;
				}

				batch.draw(topTube, tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i]);
				batch.draw(bottomTube, tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i]);

				topTubeRectangles[i].set(tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i], topTube.getWidth(), topTube.getHeight());
				bottomTubeRectangles[i].set(tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i], bottomTube.getWidth(), bottomTube.getHeight());
			}

			if (birdY > 0) {
				velocity += gravity;
				birdY -= velocity;
			} else {
				gameState = 2; // Game Over
			}

			// Draw the bird only when game is running
			batch.draw(birds[flapState], Gdx.graphics.getWidth() / 2 - birds[flapState].getWidth() / 2, birdY);

			if (flapState == 0) {
				flapState = 1;
			} else {
				flapState = 0;
			}

		} else if (gameState == 0) { // Initial State
			// Position the "Play" text at the center of the screen
			float playTextX = Gdx.graphics.getWidth() / 2f - playText.getWidth() / 2f;
			float playTextY = Gdx.graphics.getHeight() / 2f + playButtonBounds.height / 2 + 20; // Place it slightly above the Play button

			// Adjust the Play button position to be below the text
			playButtonBounds.set(
					Gdx.graphics.getWidth() / 2f - playButtonBounds.width / 2f,
					playTextY - playButtonBounds.height - 20,
					playButtonBounds.width,
					playButtonBounds.height
			);

			// Draw the Play text and button
			batch.draw(playText, playTextX, playTextY);
			batch.draw(playButton, playButtonBounds.x, playButtonBounds.y, playButtonBounds.width, playButtonBounds.height);

			// Handle touch input
			if (Gdx.input.justTouched()) {
				float touchX = Gdx.input.getX();
				float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

				if (playButtonBounds.contains(touchX, touchY)) {
					gameState = 1; // Start the game
				}
			}
		}
		else if (gameState == 2) { // Game Over State
			// Draw the "Game Over" text at the center
			float gameOverX = Gdx.graphics.getWidth() / 2 - gameover.getWidth() / 2;
			float gameOverY = Gdx.graphics.getHeight() / 2 - gameover.getHeight() / 2;
			batch.draw(gameover, gameOverX, gameOverY);

			// Position buttons below the "Game Over" text
			float buttonY = gameOverY - playButtonBounds.height - 50; // Place buttons 50px below the "Game Over" image
			float totalButtonWidth = playButtonBounds.width + exitButtonBounds.width + 20; // Total width including spacing
			float buttonsStartX = Gdx.graphics.getWidth() / 2 - totalButtonWidth / 2; // Center buttons horizontally

			// Update Play button position
			playButtonBounds.set(
					buttonsStartX,
					buttonY,
					playButtonBounds.width,
					playButtonBounds.height
			);

			// Update Exit button position
			exitButtonBounds.set(
					buttonsStartX + playButtonBounds.width + 20, // Position to the right of Play button
					buttonY,
					exitButtonBounds.width,
					exitButtonBounds.height
			);

			// Draw buttons
			batch.draw(playButton, playButtonBounds.x, playButtonBounds.y, playButtonBounds.width, playButtonBounds.height);
			batch.draw(exitButton, exitButtonBounds.x, exitButtonBounds.y, exitButtonBounds.width, exitButtonBounds.height);

			// Handle touch input
			if (Gdx.input.justTouched()) {
				float touchX = Gdx.input.getX();
				float touchY = Gdx.graphics.getHeight() - Gdx.input.getY(); // Invert Y for screen coordinates

				if (playButtonBounds.contains(touchX, touchY)) {
					gameState = 1; // Restart the game
					startGame();
					score = 0;
					scoringTube = 0;
					velocity = 0;
				} else if (exitButtonBounds.contains(touchX, touchY)) {
					Gdx.app.exit(); // Exit the game
				}
			}
		}


		font.draw(batch, String.valueOf(score), 100, 200);

		// Collision detection
		if (gameState == 1) {
			birdCircle.set(Gdx.graphics.getWidth() / 2, birdY + birds[flapState].getHeight() / 2, birds[flapState].getWidth() / 2);
			for (int i = 0; i < numberOfTubes; i++) {
				if (Intersector.overlaps(birdCircle, topTubeRectangles[i]) || Intersector.overlaps(birdCircle, bottomTubeRectangles[i])) {
					gameState = 2; // Game Over
				}
			}
		}

		batch.end();
	}

}
