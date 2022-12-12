package com.example.mvpbot.Configuration;

import com.example.mvpbot.Entity.User;
import com.example.mvpbot.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MVPBot extends TelegramLongPollingBot {

    private final UserRepository userRepository;

    boolean startWait;

    User user = new User();
    private Long adminId = 356840503L;
    private Long chatId;

    @Override
    public String getBotUsername() {
        return "@TestFedagBot";
    }

    @Override
    public String getBotToken() {
        return "5790567628:AAGYZZ2jOC8lmTGCTOZAPIRJ97w8daZIiwc";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().getText().equals("/start") && update.getMessage().hasText() && startWait == false ) {
            startWait = false;

            InlineKeyboardButton button1;
            InlineKeyboardButton button2;

            InlineKeyboardMarkup replyKeyboardMarkup = new InlineKeyboardMarkup();

            List<InlineKeyboardButton> inlineKeyboardButtons = new ArrayList<>();

            button1 = InlineKeyboardButton.builder()
                    .text("Продукт 1")
                    .callbackData("productOne")
                    .build();

            button2 = InlineKeyboardButton.builder()
                    .text("Продукт 2")
                    .callbackData("productSecond")
                    .build();


            inlineKeyboardButtons.add(button1);
            inlineKeyboardButtons.add(button2);
            replyKeyboardMarkup.setKeyboard(Collections.singletonList(inlineKeyboardButtons));

            SendMessage sendMessage = SendMessage.builder()
                    .chatId(update.getMessage().getChatId())
                    .text("Выберите продукт:")
                    .replyMarkup(replyKeyboardMarkup)
                    .build();
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }


        } else if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals("productOne")) {

            InlineKeyboardButton button1;

            InlineKeyboardMarkup replyKeyboardMarkup = new InlineKeyboardMarkup();

            List<InlineKeyboardButton> inlineKeyboardButtons = new ArrayList<>();
            button1 = InlineKeyboardButton.builder()
                    .text("Оплатить")
                    .callbackData("Оплата")
                    .build();


            inlineKeyboardButtons.add(button1);
            replyKeyboardMarkup.setKeyboard(Collections.singletonList(inlineKeyboardButtons));

            SendMessage sendMessagePr1 = SendMessage.builder()
                    .chatId(update.getCallbackQuery().getMessage().getChatId())
                    .text("Видео с описанием ценности продукта и того, как это работает")
                    .replyMarkup(replyKeyboardMarkup)
                    .build();
            try {
                execute(sendMessagePr1);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        } else if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals("productSecond")) {

            InlineKeyboardButton button1;

            InlineKeyboardMarkup replyKeyboardMarkup = new InlineKeyboardMarkup();

            List<InlineKeyboardButton> inlineKeyboardButtons = new ArrayList<>();
            button1 = InlineKeyboardButton.builder()
                    .text("Категории")
                    .callbackData("Категории")
                    .build();


            inlineKeyboardButtons.add(button1);
            replyKeyboardMarkup.setKeyboard(Collections.singletonList(inlineKeyboardButtons));

            SendMessage sendMessagePr1 = SendMessage.builder()
                    .chatId(update.getCallbackQuery().getMessage().getChatId())
                    .text("Видео с описанием ценности продукта и того, как это работает")
                    .replyMarkup(replyKeyboardMarkup)
                    .build();
            try {
                execute(sendMessagePr1);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        } else if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals("Категории")) {

            InlineKeyboardButton button1;
            InlineKeyboardButton button2;
            InlineKeyboardButton button3;

            InlineKeyboardMarkup replyKeyboardMarkup = new InlineKeyboardMarkup();

            List<InlineKeyboardButton> inlineKeyboardButtons = new ArrayList<>();
            List<InlineKeyboardButton> inlineKeyboardButtonsRow2 = new ArrayList<>();
            List<InlineKeyboardButton> inlineKeyboardButtonsRow3 = new ArrayList<>();

            button1 = InlineKeyboardButton.builder()
                    .text("Доступ к видеоконтенту")
                    .callbackData("Доступ к видеоконтенту")
                    .build();

            button2 = InlineKeyboardButton.builder()
                    .text("Консультация эксперта")
                    .callbackData("Консультация эксперта")
                    .build();

            button3 = InlineKeyboardButton.builder()
                    .text("Категория 1")
                    .callbackData("Категория 3")
                    .build();

            inlineKeyboardButtons.add(button1);
            inlineKeyboardButtonsRow2.add(button2);
            inlineKeyboardButtonsRow3.add(button3);

            replyKeyboardMarkup.setKeyboard(List.of(inlineKeyboardButtons, inlineKeyboardButtonsRow2, inlineKeyboardButtonsRow3));

            SendMessage sendMessagePr1 = SendMessage.builder()
                    .chatId(update.getCallbackQuery().getMessage().getChatId())
                    .text(update.getCallbackQuery().getData())
                    .replyMarkup(replyKeyboardMarkup)
                    .build();
            try {
                execute(sendMessagePr1);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        } else if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals("Доступ к видеоконтенту")) {

            InlineKeyboardButton button1;

            InlineKeyboardMarkup replyKeyboardMarkup = new InlineKeyboardMarkup();

            List<InlineKeyboardButton> inlineKeyboardButtons = new ArrayList<>();


            button1 = InlineKeyboardButton.builder()
                    .text("Дальше")
                    .callbackData("Дальше")
                    .build();

            inlineKeyboardButtons.add(button1);


            replyKeyboardMarkup.setKeyboard(List.of(inlineKeyboardButtons));

            SendMessage sendMessagePr1 = SendMessage.builder()
                    .chatId(update.getCallbackQuery().getMessage().getChatId())
                    .text(update.getCallbackQuery().getData())
                    .replyMarkup(replyKeyboardMarkup)
                    .build();
            try {
                execute(sendMessagePr1);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        } else if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals("Дальше")) {
            user = userRepository.findByChatId(update.getCallbackQuery().getMessage().getChatId());
            if(user.getVideoCounter() < 4){

                InlineKeyboardButton button1;

                InlineKeyboardMarkup replyKeyboardMarkup = new InlineKeyboardMarkup();

                List<InlineKeyboardButton> inlineKeyboardButtons = new ArrayList<>();


                button1 = InlineKeyboardButton.builder()
                        .text("Дальше")
                        .callbackData("Дальше")
                        .build();

                inlineKeyboardButtons.add(button1);


                replyKeyboardMarkup.setKeyboard(List.of(inlineKeyboardButtons));


                SendMessage sendMessagePr1 = SendMessage.builder()
                        .chatId(update.getCallbackQuery().getMessage().getChatId())
                        .text("Демонстрация Видео")
                        .replyMarkup(replyKeyboardMarkup)
                        .build();
                try {
                    execute(sendMessagePr1);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
                int counter = user.getVideoCounter() + 1;
                user.setVideoCounter(counter);
                userRepository.save(user);

            }
            else {
                InlineKeyboardButton button1;

                InlineKeyboardMarkup replyKeyboardMarkup = new InlineKeyboardMarkup();

                List<InlineKeyboardButton> inlineKeyboardButtons = new ArrayList<>();


                button1 = InlineKeyboardButton.builder()
                        .text("Вернуться к категориям")
                        .callbackData("productSecond")
                        .build();

                inlineKeyboardButtons.add(button1);


                replyKeyboardMarkup.setKeyboard(List.of(inlineKeyboardButtons));

                SendMessage sendMessagePr1 = SendMessage.builder()
                        .chatId(chatId)
                        .replyMarkup(replyKeyboardMarkup)
                        .text("Нажмите кнопку ниже для продолжения взаимодействия с ботом")
                        .build();
                try {
                    execute(sendMessagePr1);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }

        } else if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals("Оплата")) {

            InlineKeyboardButton button1;
            InlineKeyboardButton button2;

            InlineKeyboardMarkup replyKeyboardMarkup = new InlineKeyboardMarkup();

            List<InlineKeyboardButton> inlineKeyboardButtons = new ArrayList<>();
            button1 = InlineKeyboardButton.builder()
                    .text("РФ")
                    .callbackData("Карта для РФ")//TODO: изменить и продолжить
                    .build();

            button2 = InlineKeyboardButton.builder()
                    .text("Другие страны")
                    .callbackData("ПЛАТИ") //TODO: изменить и продолжить
                    .build();


            inlineKeyboardButtons.add(button1);
            inlineKeyboardButtons.add(button2);
            replyKeyboardMarkup.setKeyboard(Collections.singletonList(inlineKeyboardButtons));

            SendMessage sendMessagePr1 = SendMessage.builder()
                    .chatId(update.getCallbackQuery().getMessage().getChatId())
                    .text("Выберите тип оплаты:")
                    .replyMarkup(replyKeyboardMarkup)
                    .build();
            try {
                execute(sendMessagePr1);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        } else if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals("Карта для РФ") && startWait == false) {

            User user = new User();
            SendMessage sendMessage = SendMessage
                    .builder()
                    .chatId(update.getCallbackQuery().getMessage().getChatId())
                    .text("Введите имя и фамилию держателя карты, с которой поступил платёж:")
                    .build();
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
            startWait = true;

            List<User> allUsers = userRepository.findAll();
            List<Long> allChatIds = new ArrayList<>();
            for (User tmp : allUsers) {
                allChatIds.add(tmp.getChatId());
            }

            if (!allChatIds.contains(update.getCallbackQuery().getMessage().getChatId())) {
                user.setChatId(update.getCallbackQuery().getMessage().getChatId());
                user.setState("none");
                user.setStartWait(false);
                user.setViewedVideo(false);
                userRepository.save(user);
            }


        } else if (update.hasMessage() && update.getMessage().hasText() && startWait == true) {

            InlineKeyboardButton button1 = InlineKeyboardButton.builder()
                    .text("Подтверждение оплаты")
                    .callbackData("Подтверждение оплаты")
                    .build();
            InlineKeyboardMarkup replyKeyboardMarkup = new InlineKeyboardMarkup();

            List<InlineKeyboardButton> inlineKeyboardButtons = new ArrayList<>();

            inlineKeyboardButtons.add(button1);

            replyKeyboardMarkup.setKeyboard(Collections.singletonList(inlineKeyboardButtons));

            user = userRepository.findByChatId(update.getMessage().getChatId());
            user.setName(update.getMessage().getText());
            userRepository.save(user);

            SendMessage sendMessagePr1 = SendMessage.builder()
                    .chatId(update.getMessage().getChatId())
                    .text(String.format("Перевод должен быть совершён по номеру карты: 1231 1231 1233 1234%n" +
                            "После перевода денег нажмите кнопку \"Подтверждение оплаты\" %n" +
                            "После подтверждения оплаты от менеджера вам придёт уведомление и вы сможете продолжить"))
                    .replyMarkup(replyKeyboardMarkup)
                    .build();


            SendMessage sendMessage = SendMessage
                    .builder()
                    .text("Имя и фамилия для карты, которые вы ввели: " + update.getMessage().getText())
                    .chatId(update.getMessage().getChatId())
                    .build();
            try {
                execute(sendMessage);
                execute(sendMessagePr1);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }

            startWait = false;



        } else if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals("Подтверждение оплаты")) {
            checkPayment(update.getCallbackQuery().getMessage().getChatId());
            chatId = update.getCallbackQuery().getMessage().getChatId();
            User user = userRepository.findByChatId(update.getCallbackQuery().getMessage().getChatId());

            InlineKeyboardButton button1 = InlineKeyboardButton.builder()
                    .text("Подтверждение оплаты")
                    .callbackData("Подтверждение оплаты от менеджера")
                    .build();


            InlineKeyboardMarkup replyKeyboardMarkup = new InlineKeyboardMarkup();

            List<InlineKeyboardButton> inlineKeyboardButtons = new ArrayList<>();

            inlineKeyboardButtons.add(button1);

            replyKeyboardMarkup.setKeyboard(Collections.singletonList(inlineKeyboardButtons));

            SendMessage sendMessage = SendMessage
                    .builder()
                    .replyMarkup(replyKeyboardMarkup)
                    .text("Пользователь " + user.getName() + " совершил оплату")
                    .chatId(adminId)
                    .build();
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        } else if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals("Подтверждение оплаты от менеджера")) {
            user = userRepository.findByChatId(chatId);
            user.setState("approved");
            user.setStartWait(false);
            user.setViewedVideo(false);
            userRepository.save(user);
            SendMessage sendMessage = SendMessage
                    .builder()
                    .text("Оплата подтвеждена, вы можете продолжить")
                    .chatId(update.getCallbackQuery().getMessage().getChatId())
                    .build();
            InlineKeyboardButton button1;
            InlineKeyboardButton button2;
            InlineKeyboardButton button3;
            InlineKeyboardButton button4;
            InlineKeyboardButton button5;
            InlineKeyboardButton button6;
            InlineKeyboardButton button7;
            InlineKeyboardButton button8;

            button1 = InlineKeyboardButton.builder()
                    .text(String.format("Ваша ссылка на видео:%n" +
                            " https://youtu.be/CZO1Rx5HUY0"))
                    .callbackData("k1")
                    .build();

            button2 = InlineKeyboardButton.builder()
                    .text("Категория 2")
                    .callbackData("k2")
                    .build();

            button3 = InlineKeyboardButton.builder()
                    .text("Категория 3")
                    .callbackData("k3")
                    .build();

            button4 = InlineKeyboardButton.builder()
                    .text("Категория 4")
                    .callbackData("k4")
                    .build();

            button5 = InlineKeyboardButton.builder()
                    .text("Категория 5")
                    .callbackData("k5")
                    .build();

            button6 = InlineKeyboardButton.builder()
                    .text("Категория 6")
                    .callbackData("k6")
                    .build();

            button7 = InlineKeyboardButton.builder()
                    .text("Категория 7")
                    .callbackData("k7")
                    .build();

            button8 = InlineKeyboardButton.builder()
                    .text("Категория 8")
                    .callbackData("k8")
                    .build();

            InlineKeyboardMarkup replyKeyboardMarkup = new InlineKeyboardMarkup();
            List<InlineKeyboardButton> row1 = new ArrayList<>();
            List<InlineKeyboardButton> row2 = new ArrayList<>();
            List<InlineKeyboardButton> row3 = new ArrayList<>();
            List<InlineKeyboardButton> row4 = new ArrayList<>();
            row1.add(button1);
            row1.add(button2);
            row2.add(button3);
            row2.add(button4);
            row3.add(button5);
            row3.add(button6);
            row4.add(button7);
            row4.add(button8);
            replyKeyboardMarkup.setKeyboard(List.of(row1, row2, row3, row4));

            SendMessage sendMessageCategory = SendMessage.builder()
                    .chatId(update.getCallbackQuery().getMessage().getChatId())
                    .text("Выберите категорию:")
                    .replyMarkup(replyKeyboardMarkup)
                    .build();
            try {
                execute(sendMessage);
                execute(sendMessageCategory);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        } else if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals("k1") && !user.isViewedVideo()) {

            InputFile video1 = new InputFile();
            File video1File = new File(getClass().getClassLoader().getResource("video1.mp4").getFile());
            video1.setMedia(video1File);
            user = userRepository.findByChatId(update.getCallbackQuery().getMessage().getChatId());
            user.setViewedVideo(true);
            userRepository.save(user);
            SendVideo sendVideo = SendVideo
                    .builder()
                    .chatId(update.getCallbackQuery().getMessage().getChatId())
                    .video(video1)
                    .build();

            try {
                execute(sendVideo);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        } else if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals("k2") && !user.isViewedVideo()) {


            user = userRepository.findByChatId(update.getCallbackQuery().getMessage().getChatId());
            user.setViewedVideo(true);
            userRepository.save(user);
            SendMessage sendMessage = SendMessage
                    .builder()
                    .chatId(update.getCallbackQuery().getMessage().getChatId())
                    .text("ПРосмотр видео")
                    .build();

            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        } else if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals("k3") && !user.isViewedVideo()) {


            user = userRepository.findByChatId(update.getCallbackQuery().getMessage().getChatId());
            user.setViewedVideo(true);
            userRepository.save(user);
            SendMessage sendMessage = SendMessage
                    .builder()
                    .chatId(update.getCallbackQuery().getMessage().getChatId())
                    .text("Просмотр видео")
                    .build();

            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        } else if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals("k4") && !user.isViewedVideo()) {


            user = userRepository.findByChatId(update.getCallbackQuery().getMessage().getChatId());
            user.setViewedVideo(true);
            userRepository.save(user);
            SendMessage sendMessage = SendMessage
                    .builder()
                    .chatId(update.getCallbackQuery().getMessage().getChatId())
                    .text("Просмотр видео")
                    .build();

            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        } else if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals("k5") && !user.isViewedVideo()) {


            user = userRepository.findByChatId(update.getCallbackQuery().getMessage().getChatId());
            user.setViewedVideo(true);
            userRepository.save(user);
            SendMessage sendMessage = SendMessage
                    .builder()
                    .chatId(update.getCallbackQuery().getMessage().getChatId())
                    .text("Просмотр видео")
                    .build();

            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        } else if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals("k6") && !user.isViewedVideo()) {


            user = userRepository.findByChatId(update.getCallbackQuery().getMessage().getChatId());
            user.setViewedVideo(true);
            userRepository.save(user);
            SendMessage sendMessage = SendMessage
                    .builder()
                    .chatId(update.getCallbackQuery().getMessage().getChatId())
                    .text("Просмотр видео")
                    .build();

            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        } else if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals("k7") && !user.isViewedVideo()) {


            user = userRepository.findByChatId(update.getCallbackQuery().getMessage().getChatId());
            user.setViewedVideo(true);
            userRepository.save(user);
            SendMessage sendMessage = SendMessage
                    .builder()
                    .chatId(update.getCallbackQuery().getMessage().getChatId())
                    .text("Просмотр видео")
                    .build();

            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        } else if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals("k8") && !user.isViewedVideo()) {


            user = userRepository.findByChatId(update.getCallbackQuery().getMessage().getChatId());
            user.setViewedVideo(true);
            userRepository.save(user);
            SendMessage sendMessage = SendMessage
                    .builder()
                    .chatId(update.getCallbackQuery().getMessage().getChatId())
                    .text("Просмотр видео")
                    .build();

            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        } else if (update.hasCallbackQuery() && user.isViewedVideo()) {
            SendMessage sendMessage = SendMessage
                    .builder()
                    .chatId(update.getCallbackQuery().getMessage().getChatId())
                    .text("Доступ к видео вам запрещён")
                    .build();

            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }

    }
    public void checkPayment(Long chatId){
        InlineKeyboardButton button1 = InlineKeyboardButton.builder()
                .text("Подтверждение оплаты")
                .callbackData("Подтверждение оплаты от менеджера")
                .build();


        InlineKeyboardMarkup replyKeyboardMarkup = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> inlineKeyboardButtons = new ArrayList<>();

        inlineKeyboardButtons.add(button1);

        replyKeyboardMarkup.setKeyboard(Collections.singletonList(inlineKeyboardButtons));

        SendMessage sendMessage = SendMessage
                .builder()
                .replyMarkup(replyKeyboardMarkup)
                .text("Пользователь " + user.getName() + " совершил оплату")
                .chatId(adminId)
                .build();
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}




