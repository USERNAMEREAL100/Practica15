class GameStats {
    String winnerName;
    char winnerChar;
    int boardSize;
    String dateTime;

    public GameStats(String winnerName, char winnerChar, int boardSize) {
        this.winnerName = winnerName;
        this.winnerChar = winnerChar;
        this.boardSize = boardSize;
        this.dateTime = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public String toString() {
        return "Дата: " + dateTime + ", Переможець: " + winnerName + " (" + winnerChar + "), Розмір поля: " + boardSize;
    }
}