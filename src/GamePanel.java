import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.util.*;
import java.util.stream.Collectors;

public class GamePanel extends JPanel {
    //assets
    private final Character character;
    private final Background background;
    private final Wall wall;
    private final Grass leftGrass;
    private final Grass rightGrass;
    private final Boss boss;
    private final ArrayList<Character> characterArrayList;
    private final ArrayList<Bullet> bullets;
    private ArrayList<Enemy> enemies;
    private ArrayList<Ability> abilities;
    private final ArrayList<Boolean> shieldOn;
    private ArrayList<BossBullet> bossBullets;
    private int difficulty = 1;
    private int score = 0;

    //class
    private final GameWindow gameWindow;
    private KeyPressed keyPressed;

    //timers
    private final Timer enemySpawnTimer;
    private final Timer moveTimer;
    private final Timer difficultyTimer;
    private final Timer abilitySpawnTimer;
    private final Timer bossBulletTimer;
    private final Timer bossMoveTimer;
    private final int spawnTimerDelay;

    //sound
    private final SoundPlayer soundPlayer;
    private final SoundPlayer backgroundSoundPlayer;


    public GamePanel(Asset characters, GameWindow gameWindow) {
        this.gameWindow = gameWindow;

        //Assets
        this.background = new Background(gameWindow);
        this.abilities = new ArrayList<>();
        this.shieldOn = new ArrayList<>();
        this.character = (Character) characters;
        this.characterArrayList = new ArrayList<>();
        this.wall = new Wall(((Character) characters).getY(), gameWindow);
        this.leftGrass = new Grass(gameWindow, 0, 0);
        this.rightGrass = new Grass(gameWindow, gameWindow.getWidth() - 25, 0);
        this.bullets = new ArrayList<>();
        this.enemies = new ArrayList<>();
        this.bossBullets = new ArrayList<>();
        this.soundPlayer = new SoundPlayer("resources/sound/BulletSound.wav");
        this.backgroundSoundPlayer = new SoundPlayer("resources/sound/proba.wav");
        this.boss = new Boss(gameWindow);
        this.spawnTimerDelay = 300;
        int abilitySpawnTimerDelay = 15000;

        //sound
        new Thread(backgroundSoundPlayer::playBackgroundMusic).start();

        //Timers
        this.enemySpawnTimer = new Timer(spawnTimerDelay, _ -> spawnEnemies(gameWindow));
        this.difficultyTimer = new Timer(50000, _ -> increaseDifficulty());
        this.moveTimer = new Timer(30, _ -> {
            moveEnemies();
            moveAbilities();
            moveBossBullet();
        });
        Timer timer = new Timer(16, _ -> {
            this.moveBullets();
            this.repaint();
        });
        timer.start();
        abilitySpawnTimer = new Timer(abilitySpawnTimerDelay, _ -> spawnAbilities(gameWindow));
        bossBulletTimer = new Timer(500, _ -> {
            fireBossBullet();
            updateBossBulletTimer();
        });
        bossMoveTimer = new Timer(500, _ -> bossMove());

        enemySpawnTimer.start();
        abilitySpawnTimer.start();
        moveTimer.start();
        difficultyTimer.start();

        //array
        shieldOn.add(false);
        characterArrayList.add(character);

        setFocusable(true);
        requestFocusInWindow();
    }

    public void spawnAbilities(GameWindow gameWindow) {
        Random random = new Random();
        int abilityType = random.nextInt(3);
        String imgPath = switch (abilityType) {
            case 0 -> "resources/img/FirstAbility.png";
            case 1 -> "resources/img/SecondAbility.png";
            case 2 -> "resources/img/ThirdAbility.png";
            default -> "";
        };
        abilities.add(new Ability(gameWindow, imgPath));
    }

    public void moveAbilities() {

        abilities = (ArrayList<Ability>) abilities.stream().peek(ability -> ability.move(difficulty)).filter(ability -> ability.getY() < character.getY()).collect(Collectors.toList());

 /*       Iterator<Ability> iterator = abilities.iterator();
        while (iterator.hasNext()) {
            Ability ability = iterator.next();
            ability.move(difficulty);
            if (ability.getY() >= character.getY()) {
                iterator.remove();
            }
        }*/

    }

    public void spawnEnemies(GameWindow gameWindow) {
        enemies.add(new Enemy(gameWindow));
    }

    public void moveEnemies() {

        enemies = (ArrayList<Enemy>) enemies.stream().peek(enemy -> enemy.move(difficulty)).filter(enemy -> {
            if (enemy.getY() >= character.getY()) {
                if (!shieldOn.contains(true)) {
                    endGame();
                } else {
                    for (int j = shieldOn.size() - 1; j >= 0; j--) {
                        if (shieldOn.get(j)) {
                            shieldOn.set(j, false);
                            gameWindow.updateLabelLives(false);
                            break;
                        }
                    }
                    repaint();
                }
                return false;
            }
            return true;
        }).collect(Collectors.toList());

        /*Iterator<Enemy> iterator = enemies.iterator();
        while (iterator.hasNext()) {
            Enemy enemy = iterator.next();
            enemy.move(difficulty);
            if (enemy.getY() >= character.getY()) {
                if (!shieldOn.contains(true)) {
                    endGame();
                    iterator.remove();
                }else{
                    for (int j = shieldOn.size() - 1; j >= 0; j--) {
                        if (shieldOn.get(j)) {
                            shieldOn.set(j, false);
                            gameWindow.updateLabelLives(false);
                            break;
                        }
                    }
                    iterator.remove();
                    repaint();
                }
            }
        }*/
    }

    public void endGame() {
        enemySpawnTimer.stop();
        moveTimer.stop();
        difficultyTimer.stop();
        if (bossBulletTimer.isRunning()) {
            bossBulletTimer.stop();
        }
        backgroundSoundPlayer.stopBackgroundMusic();
        gameWindow.exit();
        new ExitWindow();
    }

    public void fireBullet(Character character1, int capacity) {
        bullets.add(new Bullet(character1.getX(), character1.getY() - 10, capacity));
        new Thread(soundPlayer::playSound).start();
    }
/*
    public void moveBullets() {
        Iterator<Bullet> iterator = bullets.iterator();
        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();
            bullet.move();
            if (bullet.getY() < 0 || (bullet.getY() <= wall.getHeight() && (bullet.getX() >= (wall.getX() - wall.getWidth() / 2) && bullet.getX() <= (wall.getX() + wall.getWidth() / 2)))) {
                iterator.remove();
            }
            Iterator<Ability> abilityIterator = abilities.iterator();
            while (abilityIterator.hasNext()) {
                Ability ability = abilityIterator.next();
                if (ability.getBounds().intersects(bullet.getBounds())) {
                    if (bullet.getCapacity() > 1) {
                        bullet.setCapacity(bullet.getCapacity() - 1);
                    } else {
                        if (bullet.isOutOfBounds()) {
                            iterator.remove();
                        }
                    }
                    if (ability.decreaseDamage() == 0) {
                        abilityIterator.remove();
                        int MAXCHARACTER = 3;
                        switch (ability.getImgPath()) {
                            case "resources/img/FirstAbility.png" -> {
                                if (MAXCHARACTER > characterArrayList.size()) {
                                    Character character1 = new Character(gameWindow, 0, 1);
                                    characterArrayList.add(character1);
                                    shieldOn.add(false);
                                    repaintCharacter();
                                } else {
                                    gameWindow.updateLabelWrong("Plus Ship");
                                }
                                //character = character1;
                            }
                            case "resources/img/SecondAbility.png" -> {
                                if (MAXCHARACTER > this.keyPressed.getCapacity()) {
                                    this.keyPressed.setCapacity(keyPressed.getCapacity() + 1);
                                    gameWindow.updateLabelBullet();
                                } else {
                                    gameWindow.updateLabelWrong("Bullet Strength");
                                }
                                //character = character1;
                            }
                            case "resources/img/ThirdAbility.png" -> {
                                for (int i = 0; i < shieldOn.size(); i++) {
                                    if (!shieldOn.get(i)) {
                                        shieldOn.set(i, true);
                                        gameWindow.updateLabelLives(true);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    break;
                }
            }
        }
        ListIterator<Bullet> bulletIterator = bullets.listIterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();

            ListIterator<Enemy> enemyIterator = enemies.listIterator();
            while (enemyIterator.hasNext()) {
                Enemy enemy = enemyIterator.next();

                if (bullet.getBounds().intersects(enemy.getBounds())) {
                    if (bullet.getCapacity() > 1) {
                        bullet.setCapacity(bullet.getCapacity() - 1);
                    } else {
                        bulletIterator.remove();
                    }
                    gameWindow.updateLabelScore();
                    score++;
                    enemyIterator.remove();
                    int timeToBoss = 1000;
                    if (score >= timeToBoss) {
                        score = 0;
                        createBoss();
                    }
                    break;
                }
            }
        }
        if (boss.isReady()) {
            ListIterator<Bullet> bulletIterator2 = bullets.listIterator();
            while (bulletIterator2.hasNext()) {
                Bullet bullet = bulletIterator2.next();
                if (bullet.getBounds().intersects(boss.getBounds())) {
                    if (boss.getHealth() == 0) {
                        backToTheGame();
                        //endGame();
                    } else {
                        boss.setHealth(boss.getHealth() - 1);
                        System.out.println(boss.getHealth());
                        bulletIterator2.remove();
                    }
                }
            }
        }
    }*/

    public void moveBullets() {
        moveAndRemoveBullets();
        checkBulletAbilityCollisions();
        checkBulletEnemyCollisions();
        checkBulletBossCollisions();
    }

    private void moveAndRemoveBullets() {
        Iterator<Bullet> iterator = bullets.iterator();
        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();
            bullet.move();
            if (bullet.getY() < 0 || (bullet.getY() <= wall.getHeight() && (bullet.getX() >= (wall.getX() - wall.getWidth() / 2) && bullet.getX() <= (wall.getX() + wall.getWidth() / 2)))) {
                iterator.remove();
            }
        }
    }

    private void checkBulletAbilityCollisions() {
        Iterator<Bullet> iterator = bullets.iterator();
        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();
            Iterator<Ability> abilityIterator = abilities.iterator();
            while (abilityIterator.hasNext()) {
                Ability ability = abilityIterator.next();
                if (ability.getBounds().intersects(bullet.getBounds())) {
                    handleBulletAbilityCollision(bullet, ability, iterator, abilityIterator);
                    break;
                }
            }
        }
    }

    private void handleBulletAbilityCollision(Bullet bullet, Ability ability, Iterator<Bullet> bulletIterator, Iterator<Ability> abilityIterator) {
        if (bullet.getCapacity() > 1) {
            bullet.setCapacity(bullet.getCapacity() - 1);
        } else {
            if (bullet.isOutOfBounds()) {
                bulletIterator.remove();
            }
        }
        if (ability.decreaseDamage() == 0) {
            abilityIterator.remove();
            handleAbilityEffect(ability);
        }
    }

    private void handleAbilityEffect(Ability ability) {
        int MAXCHARACTER = 3;
        switch (ability.getImgPath()) {
            case "resources/img/FirstAbility.png" -> {
                if (MAXCHARACTER > characterArrayList.size()) {
                    Character character1 = new Character(gameWindow, 0, 1);
                    characterArrayList.add(character1);
                    shieldOn.add(false);
                    repaintCharacter();
                } else {
                    gameWindow.updateLabelWrong("Plus Ship");
                }
            }
            case "resources/img/SecondAbility.png" -> {
                if (MAXCHARACTER > this.keyPressed.getCapacity()) {
                    this.keyPressed.setCapacity(keyPressed.getCapacity() + 1);
                    gameWindow.updateLabelBullet();
                } else {
                    gameWindow.updateLabelWrong("Bullet Strength");
                }
            }
            case "resources/img/ThirdAbility.png" -> {
                for (int i = 0; i < shieldOn.size(); i++) {
                    if (!shieldOn.get(i)) {
                        shieldOn.set(i, true);
                        gameWindow.updateLabelLives(true);
                        break;
                    }
                }
            }
        }
    }

    private void checkBulletEnemyCollisions() {
        ListIterator<Bullet> bulletIterator = bullets.listIterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            ListIterator<Enemy> enemyIterator = enemies.listIterator();
            while (enemyIterator.hasNext()) {
                Enemy enemy = enemyIterator.next();
                if (bullet.getBounds().intersects(enemy.getBounds())) {
                    handleBulletEnemyCollision(bullet, bulletIterator, enemyIterator);
                    break;
                }
            }
        }
    }

    private void handleBulletEnemyCollision(Bullet bullet, ListIterator<Bullet> bulletIterator, ListIterator<Enemy> enemyIterator) {
        if (bullet.getCapacity() > 1) {
            bullet.setCapacity(bullet.getCapacity() - 1);
        } else {
            bulletIterator.remove();
        }
        gameWindow.updateLabelScore();
        score++;
        enemyIterator.remove();
        int timeToBoss = 1000;
        if (score >= timeToBoss) {
            score = 0;
            createBoss();
        }
    }

    private void checkBulletBossCollisions() {
        if (boss.isReady()) {
            ListIterator<Bullet> bulletIterator = bullets.listIterator();
            while (bulletIterator.hasNext()) {
                Bullet bullet = bulletIterator.next();
                if (bullet.getBounds().intersects(boss.getBounds())) {
                    handleBulletBossCollision(bullet, bulletIterator);
                }
            }
        }
    }

    private void handleBulletBossCollision(Bullet bullet, ListIterator<Bullet> bulletIterator) {
        if (boss.getHealth() == 0) {
            backToTheGame();
        } else {
            boss.setHealth(boss.getHealth() - 1);
            bulletIterator.remove();
        }
    }

    public void repaintCharacter() {
        Iterator<Character> characterIterator = characterArrayList.iterator();
        int max = characterArrayList.size();
        int iteratorValue = 0;
        while (characterIterator.hasNext()) {
            Character character = characterIterator.next();
            character.setX(max, iteratorValue);
            character.setShieldX(character.getX());
            iteratorValue++;
        }
        repaint();
    }

    public void createBoss() {
        wall.setVisible(false);
        background.setChoice(false);
        enemySpawnTimer.stop();
        abilitySpawnTimer.stop();

        enemies.clear();
        abilities.clear();
        boss.setActive(true);
        repaint();
        Timer checkBossReadyTimer = new Timer(50, e -> {
            if (boss.isReady()) {
                bossBulletTimer.start();
                bossMoveTimer.start();
                ((Timer) e.getSource()).stop();
            }
        });
        checkBossReadyTimer.start();
    }

    public void bossMove() {
        Random random = new Random();
        boss.setX(random.nextInt(gameWindow.getWidth() - boss.getWidth() - boss.getWidth() / 2) + boss.getWidth() / 2);

    }

    public void fireBossBullet() {
        bossBullets.add(new BossBullet(boss));
    }

    public void moveBossBullet() {
        bossBullets = (ArrayList<BossBullet>) bossBullets.stream().peek(BossBullet::move).filter(bossBullet -> {
            if (bossBullet.getY() >= character.getY() && bossBullet.getX() + bossBullet.getWidth() / 2 >= character.getX() - character.getWidth() / 2 && bossBullet.getX() - bossBullet.getWidth() / 2 <= characterArrayList.getLast().getX() + characterArrayList.getLast().getWidth() / 2) {
                endGame();
            } else return bossBullet.getY() < character.getY();
            return false;
        }).collect(Collectors.toList());

    }

    public void backToTheGame() {
        wall.setVisible(true);
        background.setChoice(true);
        enemySpawnTimer.start();
        abilitySpawnTimer.start();
        boss.setActive(false);
        bossBulletTimer.stop();
        bossBullets.clear();
        boss.setHealth(100);
        repaint();
    }

    private void increaseDifficulty() {
        difficulty++;
        gameWindow.updateLabelLevel();
        enemySpawnTimer.setDelay(spawnTimerDelay / (2 * difficulty));
        System.out.println("Nehézségi szint: " + difficulty);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        background.printImg(g);
        wall.printImg(g);
        rightGrass.printImg(g);
        leftGrass.printImg(g);
        int i = 0;
        for (Character character1 : characterArrayList) {
            character1.printImg(g, shieldOn.get(i));
            i++;
        }
        for (Bullet bullet : bullets) {
            bullet.printImg(g);
        }
        for (Enemy enemy : enemies) {
            enemy.printImg(g);
        }
        for (Ability ability : abilities) {
            ability.printImg(g);
        }
        if (boss.getActive()) {
            boss.printImg(g);
        }
        for (BossBullet bossBullet : bossBullets) {
            bossBullet.printImg(g);
        }
    }

    public void setKeyPressed(KeyPressed keyPressed) {
        this.keyPressed = keyPressed;
    }

    public ArrayList<Character> getCharacterArrayList() {
        return characterArrayList;
    }

    private void updateBossBulletTimer() {
        Random random = new Random();
        bossBulletTimer.setDelay(random.nextInt(700));
    }
}
