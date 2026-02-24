package pt.tecnico.ttt.server;

import io.grpc.stub.StreamObserver;
import pt.tecnico.ttt.*;

public class TTTServiceImpl extends TTTGrpc.TTTImplBase {

	/** Game implementation. */
	private TTTGame ttt = new TTTGame();

	@Override
	public void currentBoard(CurrentBoardRequest request, StreamObserver<CurrentBoardResponse> responseObserver) {
		// StreamObserver is used to represent the gRPC stream between the server and
		// client in order to send the appropriate responses (or errors, if any occur).

		CurrentBoardResponse response = CurrentBoardResponse.newBuilder().setBoard(ttt.toString()).build();

		// Send a single response through the stream.
		responseObserver.onNext(response);
		// Notify the client that the operation has been completed.
		responseObserver.onCompleted();
	}

	@Override
	public void play(PlayRequest request, StreamObserver<PlayResponse> responseObserver) {
		//? get the inputs 
		int x = request.getX();
		int y = request.getY();
		int player = request.getPlayer();
		//? play the game
		PlayResult result = ttt.play(x, y, player);
		//? build the response
		PlayResponse response = PlayResponse.newBuilder().setResult(result).build();
		//? send the response
		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}

	@Override
	public void checkWinner(CheckWinnerRequest request, StreamObserver<CheckWinnerResponse> responseObserver) {
		//? check the winner
		int winner = ttt.checkWinner();
		//?  build the response
		CheckWinnerResponse response = CheckWinnerResponse.newBuilder().setWinner(winner).build();
		//? send the response
		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}
}