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

    // 734492104 - Kirill
    // 356840503 глб
    // лил 5105642633
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
        if (update.hasMessage() && update.getMessage().getText().equals("/start") && update.getMessage().hasText() && startWait == false) {
            User newUser = new User();
            List<User> allUsers = userRepository.findAll();
            List<Long> allChatIds = new ArrayList<>();
            for (User tmp : allUsers) {
                allChatIds.add(tmp.getChatId());
            }

            if (!allChatIds.contains(update.getMessage().getChatId())) {
                newUser.setChatId(update.getMessage().getChatId());
                newUser.setState("none");
                newUser.setStartWait(false);
                newUser.setViewedVideo(false);
                userRepository.save(newUser);
            }

            InlineKeyboardButton button1;
            InlineKeyboardButton button2;
            InlineKeyboardButton button3;
            InlineKeyboardButton button4;

            InlineKeyboardMarkup replyKeyboardMarkup = new InlineKeyboardMarkup();

            List<InlineKeyboardButton> inlineKeyboardButtonsRow1 = new ArrayList<>();
            List<InlineKeyboardButton> inlineKeyboardButtonsRow2 = new ArrayList<>();
            button1 = InlineKeyboardButton.builder()
                    .text("Категория 1")
                    .callbackData("Category1")
                    .build();

            button2 = InlineKeyboardButton.builder()
                    .text("Категория 2")
                    .callbackData("Category2")
                    .build();

            button3 = InlineKeyboardButton.builder()
                    .text("Категория 3")
                    .callbackData("Category3")
                    .build();

            button4 = InlineKeyboardButton.builder()
                    .text("Категория 4")
                    .callbackData("Category4")
                    .build();

            inlineKeyboardButtonsRow1.add(button1);
            inlineKeyboardButtonsRow1.add(button2);
            inlineKeyboardButtonsRow2.add(button3);
            inlineKeyboardButtonsRow2.add(button4);
            replyKeyboardMarkup.setKeyboard(List.of(inlineKeyboardButtonsRow1, inlineKeyboardButtonsRow2));

            SendMessage sendMessage = SendMessage.builder()
                    .chatId(update.getMessage().getChatId())
                    .text("Приветствие и о чём продукт")
                    .build();


            SendMessage sendMessageAboutProduct = SendMessage
                    .builder()
                    .chatId(update.getMessage().getChatId())
                    .text(String.format("Выбери то, что волнует сейчас больше всего?%nЯ хочу перейти …."))
                    .replyMarkup(replyKeyboardMarkup)
                    .build();
            try {
                execute(sendMessage);
                execute(sendMessageAboutProduct);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        } else if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals("Category1")) {
            user = userRepository.findByChatId(update.getCallbackQuery().getMessage().getChatId());
            user.setCategory("category1");
            userRepository.save(user);
            InlineKeyboardButton button1;
            InlineKeyboardButton button2;

            InlineKeyboardMarkup replyKeyboardMarkup = new InlineKeyboardMarkup();

            List<InlineKeyboardButton> inlineKeyboardButtons = new ArrayList<>();
            button1 = InlineKeyboardButton.builder()
                    .text("РФ")
                    .callbackData("Карта для РФ Category1")//TODO: изменить и продолжить
                    .build();

            button2 = InlineKeyboardButton.builder()
                    .text("Другие страны")
                    .callbackData("ПЛАТИ Category1") //TODO: изменить и продолжить
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
        } else if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals("Карта для РФ Category1") && startWait == false) {

            User user = new User();
            SendMessage sendMessage = SendMessage
                    .builder()
                    .chatId(update.getCallbackQuery().getMessage().getChatId())
                    .text("Введите имя и фамилию держателя карты, с которой поступит платёж:")
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
            user = userRepository.findByChatId(update.getMessage().getChatId());
            if (user.getCategory().equals("category1")) {
                InlineKeyboardButton button1 = InlineKeyboardButton.builder()
                        .text("Подтверждение оплаты")
                        .callbackData("Подтверждение оплаты Category1")
                        .build();
                InlineKeyboardMarkup replyKeyboardMarkup = new InlineKeyboardMarkup();

                List<InlineKeyboardButton> inlineKeyboardButtons = new ArrayList<>();

                inlineKeyboardButtons.add(button1);

                replyKeyboardMarkup.setKeyboard(Collections.singletonList(inlineKeyboardButtons));


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
            } else if (user.getCategory().equals("category2")) {

                InlineKeyboardButton button1 = InlineKeyboardButton.builder()
                        .text("Подтверждение оплаты")
                        .callbackData("Подтверждение оплаты Category2")
                        .build();
                InlineKeyboardMarkup replyKeyboardMarkup = new InlineKeyboardMarkup();

                List<InlineKeyboardButton> inlineKeyboardButtons = new ArrayList<>();

                inlineKeyboardButtons.add(button1);

                replyKeyboardMarkup.setKeyboard(Collections.singletonList(inlineKeyboardButtons));


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
            } else if (user.getCategory().equals("category3")) {

                InlineKeyboardButton button1 = InlineKeyboardButton.builder()
                        .text("Подтверждение оплаты")
                        .callbackData("Подтверждение оплаты Category3")
                        .build();
                InlineKeyboardMarkup replyKeyboardMarkup = new InlineKeyboardMarkup();

                List<InlineKeyboardButton> inlineKeyboardButtons = new ArrayList<>();

                inlineKeyboardButtons.add(button1);

                replyKeyboardMarkup.setKeyboard(Collections.singletonList(inlineKeyboardButtons));


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
            } else if (user.getCategory().equals("category4")) {

                InlineKeyboardButton button1 = InlineKeyboardButton.builder()
                        .text("Подтверждение оплаты")
                        .callbackData("Подтверждение оплаты Category4")
                        .build();
                InlineKeyboardMarkup replyKeyboardMarkup = new InlineKeyboardMarkup();

                List<InlineKeyboardButton> inlineKeyboardButtons = new ArrayList<>();

                inlineKeyboardButtons.add(button1);

                replyKeyboardMarkup.setKeyboard(Collections.singletonList(inlineKeyboardButtons));


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
            }
        } else if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals("Подтверждение оплаты Category1")) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
            User user = userRepository.findByChatId(update.getCallbackQuery().getMessage().getChatId());


            InlineKeyboardButton button1 = InlineKeyboardButton.builder()
                    .text("Подтверждение оплаты")
                    .callbackData("Подтверждение Category1")
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
        } else if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals("Подтверждение Category1")) {

            user = userRepository.findByChatId(chatId);
            user.setState("approved");
            user.setStartWait(false);
            user.setViewedVideo(false);
            userRepository.save(user);
            SendMessage sendMessage = SendMessage
                    .builder()
                    .text(String.format("Ссылка на видео в YouTube%n https://youtu.be/CZO1Rx5HUY0"))
                    .chatId(chatId)
                    .build();
            SendMessage sendMessage1 = SendMessage
                    .builder()
                    .text("Послесловие")
                    .chatId(chatId)
                    .build();
            try {
                execute(sendMessage);
                execute(sendMessage1);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
            // Пошла категория 2 ------------------------
        } else if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals("Category2")) {
            user = userRepository.findByChatId(update.getCallbackQuery().getMessage().getChatId());
            user.setCategory("category2");
            userRepository.save(user);
            InlineKeyboardButton button1;
            InlineKeyboardButton button2;

            InlineKeyboardMarkup replyKeyboardMarkup = new InlineKeyboardMarkup();

            List<InlineKeyboardButton> inlineKeyboardButtons = new ArrayList<>();
            button1 = InlineKeyboardButton.builder()
                    .text("РФ")
                    .callbackData("Карта для РФ Category2")//TODO: изменить и продолжить
                    .build();

            button2 = InlineKeyboardButton.builder()
                    .text("Другие страны")
                    .callbackData("ПЛАТИ Category2") //TODO: изменить и продолжить
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
        } else if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals("Карта для РФ Category2") && startWait == false) {

            User user = new User();
            SendMessage sendMessage = SendMessage
                    .builder()
                    .chatId(update.getCallbackQuery().getMessage().getChatId())
                    .text("Введите имя и фамилию держателя карты, с которой поступит платёж:")
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


        } else if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals("Подтверждение оплаты Category2")) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
            User user = userRepository.findByChatId(update.getCallbackQuery().getMessage().getChatId());

            InlineKeyboardButton button1 = InlineKeyboardButton.builder()
                    .text("Подтверждение оплаты")
                    .callbackData("Подтверждение Category2")
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
        } else if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals("Подтверждение Category2")) {
            user = userRepository.findByChatId(chatId);
            user.setState("approved");
            user.setStartWait(false);
            user.setViewedVideo(false);
            userRepository.save(user);
            SendMessage sendMessage = SendMessage
                    .builder()
                    .text("Просмотр видео из категории 2")
                    .chatId(chatId)
                    .build();

            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }

        //Пошла третья ======

        else if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals("Category3")) {
            user = userRepository.findByChatId(update.getCallbackQuery().getMessage().getChatId());
            user.setCategory("category3");
            userRepository.save(user);
            InlineKeyboardButton button1;
            InlineKeyboardButton button2;

            InlineKeyboardMarkup replyKeyboardMarkup = new InlineKeyboardMarkup();

            List<InlineKeyboardButton> inlineKeyboardButtons = new ArrayList<>();
            button1 = InlineKeyboardButton.builder()
                    .text("РФ")
                    .callbackData("Карта для РФ Category3")//TODO: изменить и продолжить
                    .build();

            button2 = InlineKeyboardButton.builder()
                    .text("Другие страны")
                    .callbackData("ПЛАТИ Category3") //TODO: изменить и продолжить
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
        } else if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals("Карта для РФ Category3") && startWait == false) {

            User user = new User();
            SendMessage sendMessage = SendMessage
                    .builder()
                    .chatId(update.getCallbackQuery().getMessage().getChatId())
                    .text("Введите имя и фамилию держателя карты, с которой поступит платёж:")
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

        } else if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals("Подтверждение оплаты Category3")) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
            User user = userRepository.findByChatId(update.getCallbackQuery().getMessage().getChatId());

            InlineKeyboardButton button1 = InlineKeyboardButton.builder()
                    .text("Подтверждение оплаты")
                    .callbackData("Подтверждение Category3")
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
        } else if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals("Подтверждение Category3")) {
            user = userRepository.findByChatId(chatId);
            user.setState("approved");
            user.setStartWait(false);
            user.setViewedVideo(false);
            userRepository.save(user);
            SendMessage sendMessage = SendMessage
                    .builder()
                    .text("Просмотр видео из категории 3")
                    .chatId(chatId)
                    .build();

            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
        // Четвертая категория -------------------------


        else if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals("Category4")) {
            user = userRepository.findByChatId(update.getCallbackQuery().getMessage().getChatId());
            user.setCategory("category4");
            userRepository.save(user);
            InlineKeyboardButton button1;
            InlineKeyboardButton button2;

            InlineKeyboardMarkup replyKeyboardMarkup = new InlineKeyboardMarkup();

            List<InlineKeyboardButton> inlineKeyboardButtons = new ArrayList<>();
            button1 = InlineKeyboardButton.builder()
                    .text("РФ")
                    .callbackData("Карта для РФ Category4")//TODO: изменить и продолжить
                    .build();

            button2 = InlineKeyboardButton.builder()
                    .text("Другие страны")
                    .callbackData("ПЛАТИ Category4") //TODO: изменить и продолжить
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
        } else if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals("Карта для РФ Category4") && startWait == false) {

            User user = new User();
            SendMessage sendMessage = SendMessage
                    .builder()
                    .chatId(update.getCallbackQuery().getMessage().getChatId())
                    .text("Введите имя и фамилию держателя карты, с которой поступит платёж:")
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


        } else if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals("Подтверждение оплаты Category4")) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
            User user = userRepository.findByChatId(update.getCallbackQuery().getMessage().getChatId());

            InlineKeyboardButton button1 = InlineKeyboardButton.builder()
                    .text("Подтверждение оплаты")
                    .callbackData("Подтверждение Category4")
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
        } else if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals("Подтверждение Category4")) {
            user = userRepository.findByChatId(chatId);
            user.setState("approved");
            user.setStartWait(false);
            user.setViewedVideo(false);
            userRepository.save(user);
            SendMessage sendMessage = SendMessage
                    .builder()
                    .text("Просмотр видео из категории 2")
                    .chatId(chatId)
                    .build();

            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }
}




