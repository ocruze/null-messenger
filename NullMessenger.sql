-- -----------------------------------------------------
-- Table `user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `user` (
  `idUser` INTEGER PRIMARY KEY AUTOINCREMENT,
  `username` VARCHAR(32) NOT NULL UNIQUE,
  `password` VARCHAR(24) NULL,
  `salt` VARCHAR(24) NULL
);


-- -----------------------------------------------------
-- Table `conversation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `conversation` (
  `idConversation` INTEGER PRIMARY KEY AUTOINCREMENT
);


-- -----------------------------------------------------
-- Table `message`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `message` (
  `idMessage` INTEGER PRIMARY KEY AUTOINCREMENT,
  `content` TEXT NOT NULL,
  `idSender` INTEGER NOT NULL,
  `idConversation` INTEGER NOT NULL,
  `date` DATETIME NOT NULL,
  CONSTRAINT `fk_Message_Sender`
    FOREIGN KEY (`idSender`)
    REFERENCES `user` (`idUser`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Message_Conversation`
    FOREIGN KEY (`idConversation`)
    REFERENCES `conversation` (`idConversation`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
);