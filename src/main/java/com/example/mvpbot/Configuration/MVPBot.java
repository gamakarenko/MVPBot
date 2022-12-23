package com.example.mvpbot.Configuration;

import com.example.mvpbot.Entity.User;
import com.example.mvpbot.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MVPBot extends TelegramLongPollingBot {

    private final UserRepository userRepository;

    boolean startWait;

    boolean anotherCountry;

    User user = new User();

    // 734492104 - Kirill
    // 356840503 глб
    // лил 5105642633
    private Long adminId = 356840503L;
    private Long secondAdminId = 413365670L;
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

            InlineKeyboardMarkup replyKeyboardMarkup = new InlineKeyboardMarkup();
            InlineKeyboardButton iCome;
            List<InlineKeyboardButton> inlineKeyboardButtons = new ArrayList<>();

            iCome = InlineKeyboardButton.builder()
                    .text("Я иду")
                    .callbackData("Я иду")
                    .build();

            inlineKeyboardButtons.add(iCome);
            replyKeyboardMarkup.setKeyboard(List.of(inlineKeyboardButtons));
            SendMessage sendMessage = SendMessage
                    .builder()
                    .chatId(update.getMessage().getChatId())
                    .text(String.format("ПРОЖИВИ%n" +
                            "ОТПУСТИ%n" +
                            "ВЫЙДИ НА НОВЫЙ УРОВНЬ%n" +
                            "ЧЕРЕЗ  ДВИЖЕНИЕ И СОЗНАНИЕ✨"))
                    .replyMarkup(replyKeyboardMarkup)
                    .build();

            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }

        }
        else if(update.hasCallbackQuery() && update.getCallbackQuery().getData().equals("Я иду")){
            InlineKeyboardMarkup replyKeyboardMarkup = new InlineKeyboardMarkup();
            User newUser = new User();
            List<User> allUsers = userRepository.findAll();
            List<Long> allChatIds = new ArrayList<>();
            for (User tmp : allUsers) {
                allChatIds.add(tmp.getChatId());
            }

            if (!allChatIds.contains(update.getCallbackQuery().getMessage().getChatId())) {
                newUser.setChatId(update.getCallbackQuery().getMessage().getChatId());
                newUser.setState("none");
                newUser.setStartWait(false);
                newUser.setViewedVideo(false);
                newUser.setRegistrationDate(LocalDateTime.now());
                userRepository.save(newUser);
            }

            InlineKeyboardButton button1;
            InlineKeyboardButton button2;
            InlineKeyboardButton button3;
            InlineKeyboardButton button4;



            List<InlineKeyboardButton> inlineKeyboardButtonsRow1 = new ArrayList<>();
            List<InlineKeyboardButton> inlineKeyboardButtonsRow2 = new ArrayList<>();
            List<InlineKeyboardButton> inlineKeyboardButtonsRow3 = new ArrayList<>();
            List<InlineKeyboardButton> inlineKeyboardButtonsRow4 = new ArrayList<>();
            button1 = InlineKeyboardButton.builder()
                    .text("Нет денег -> изобилие")
                    .callbackData("Category1")
                    .build();

            button2 = InlineKeyboardButton.builder()
                    .text("Одиночество -> любовь")
                    .callbackData("Category2")
                    .build();

            button3 = InlineKeyboardButton.builder()
                    .text("Тревога -> спокойствие")
                    .callbackData("Category3")
                    .build();

            button4 = InlineKeyboardButton.builder()
                    .text("Страх -> проявленность")
                    .callbackData("Category4")
                    .build();

            inlineKeyboardButtonsRow1.add(button1);
            inlineKeyboardButtonsRow2.add(button2);
            inlineKeyboardButtonsRow3.add(button3);
            inlineKeyboardButtonsRow4.add(button4);
            replyKeyboardMarkup.setKeyboard(List.of(inlineKeyboardButtonsRow1, inlineKeyboardButtonsRow2, inlineKeyboardButtonsRow3, inlineKeyboardButtonsRow4));


            SendMessage sendMessageAboutProduct = SendMessage
                    .builder()
                    .chatId(update.getCallbackQuery().getMessage().getChatId())
                    .text(String.format("Выбери то, что волнует сейчас больше всего?%nЯ хочу перейти из ..."))
                    .replyMarkup(replyKeyboardMarkup)
                    .build();
            try {
                execute(sendMessageAboutProduct);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        } else if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals("Category1")) {
            user = userRepository.findByChatId(update.getCallbackQuery().getMessage().getChatId());
            user.setCategory("category1");
            user.setRegistrationDate(LocalDateTime.now());
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
        }
        else if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals("ПЛАТИ Category1") && startWait == false){
            user = userRepository.findByChatId(update.getCallbackQuery().getMessage().getChatId());
            SendMessage sendMessage = SendMessage
                    .builder()
                    .chatId(update.getCallbackQuery().getMessage().getChatId())
                    .text(String.format("Zirat TR86 0001 0090 1015 8920 7050 01%n" +
                            "Стоимость 555 Tl%n" +
                            "Toncoin UQClPe72ZZb-VbOtdbMREpjVjpsjONCHEElpC3psp62KrUqc%n" +
                            "Стоимость 14,3 Toncoin%n%n" +
                            "Введите имя пользователя, от которого поступит платёж"))
                    .build();
            anotherCountry = true;
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }

        else if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals("ПЛАТИ Category2") && startWait == false){
            user = userRepository.findByChatId(update.getCallbackQuery().getMessage().getChatId());
            SendMessage sendMessage = SendMessage
                    .builder()
                    .chatId(update.getCallbackQuery().getMessage().getChatId())
                    .text(String.format("Zirat TR86 0001 0090 1015 8920 7050 01%n" +
                            "Стоимость 555 Tl%n" +
                            "Toncoin UQClPe72ZZb-VbOtdbMREpjVjpsjONCHEElpC3psp62KrUqc%n" +
                            "Стоимость 14,3 Toncoin%n%n" +
                            "Введите имя пользователя, от которого поступит платёж"))
                    .build();
            anotherCountry = true;
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }

        else if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals("ПЛАТИ Category3") && startWait == false){
            user = userRepository.findByChatId(update.getCallbackQuery().getMessage().getChatId());
            SendMessage sendMessage = SendMessage
                    .builder()
                    .chatId(update.getCallbackQuery().getMessage().getChatId())
                    .text(String.format("Zirat TR86 0001 0090 1015 8920 7050 01%n" +
                            "Стоимость 555 Tl%n" +
                            "Toncoin UQClPe72ZZb-VbOtdbMREpjVjpsjONCHEElpC3psp62KrUqc%n" +
                            "Стоимость 14,3 Toncoin%n%n" +
                            "Введите имя пользователя, от которого поступит платёж"))
                    .build();
            anotherCountry = true;
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }

        else if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals("ПЛАТИ Category4") && startWait == false){
            user = userRepository.findByChatId(update.getCallbackQuery().getMessage().getChatId());
            SendMessage sendMessage = SendMessage
                    .builder()
                    .chatId(update.getCallbackQuery().getMessage().getChatId())
                    .text(String.format("Zirat TR86 0001 0090 1015 8920 7050 01%n" +
                            "Стоимость 555 Tl%n" +
                            "Toncoin UQClPe72ZZb-VbOtdbMREpjVjpsjONCHEElpC3psp62KrUqc%n" +
                            "Стоимость 14,3 Toncoin%n%n" +
                            "Введите имя пользователя, от которого поступит платёж"))
                    .build();
            anotherCountry = true;
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
        else if (update.hasMessage() && update.getMessage().hasText() &&  anotherCountry == true){
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
                        .text(String.format("После перевода денег нажмите кнопку \"Подтверждение оплаты\" %n" +
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
                        .text(String.format("После перевода денег нажмите кнопку \"Подтверждение оплаты\" %n" +
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
                        .text(String.format("После перевода денег нажмите кнопку \"Подтверждение оплаты\" %n" +
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
                        .text(String.format("После перевода денег нажмите кнопку \"Подтверждение оплаты\" %n" +
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
        }
        else if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals("Карта для РФ Category1") && startWait == false) {

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
                        .text(String.format("Перевод должен быть совершён по номеру карты:%nСбербанк: 5469 3801 2768 1624%nТинькоф: 5536 9138 2009 6671%nСтоимость 2222 руб%n " +
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
                        .text(String.format("Перевод должен быть совершён по номеру карты:%nСбербанк: 5469 3801 2768 1624%nТинькоф: 5536 9138 2009 6671%nСтоимость 2222 руб%n" +
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
                        .text(String.format("Перевод должен быть совершён по номеру карты:%nСбербанк: 5469 3801 2768 1624%nТинькоф: 5536 9138 2009 6671%nСтоимость 2222 руб%n" +
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
                        .text(String.format("Перевод должен быть совершён по номеру карты:%nСбербанк: 5469 3801 2768 1624%nТинькоф: 5536 9138 2009 6671%nСтоимость 2222 руб%n" +
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
            SendMessage sendMessage2 = SendMessage
                    .builder()
                    .replyMarkup(replyKeyboardMarkup)
                    .text("Пользователь " + user.getName() + " совершил оплату")
                    .chatId(secondAdminId)
                    .build();
            try {
                execute(sendMessage);
                execute(sendMessage2);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        } else if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals("Подтверждение Category1")) {

            user = userRepository.findByChatId(chatId);

            if(!user.getState().equals("approved")) {
                SendMessage sendMessageInfo = SendMessage
                        .builder()
                        .text(String.format("Видео будет доступно 30 дней после покупки!%n Возможно, для просмотра видео понадобится включить впн, рекомендуемая страна - Франция"))
                        .chatId(chatId)
                        .build();
                SendMessage sendMessage = SendMessage
                        .builder()
                        .text(String.format("Ссылка на видео в YouTube%nhttps://youtu.be/iZ650tzI2nI"))
                        .chatId(chatId)
                        .build();

                user.setState("approved");
                user.setStartWait(false);
                user.setViewedVideo(false);
                userRepository.save(user);

                Timer timerForMessageAfter = new Timer(5000, null);

                timerForMessageAfter.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        SendMessage sendMessage1 = SendMessage
                                .builder()
                                .text(String.format("Как всё прошло? Какие ощущения? Проект создан от всего сердца.%n" +
                                        "Передай самым дорогим людям вход в P O R T A L https://t.me/portalnewlife"))
                                .chatId(chatId)
                                .build();
                        try {
                            execute(sendMessage1);
                            userRepository.delete(user);
                        } catch (TelegramApiException ex) {
                            throw new RuntimeException(ex);
                        }

                    }
                });

                timerForMessageAfter.setRepeats(false);
                timerForMessageAfter.setDelay(5000); //5 sec
                timerForMessageAfter.start();
                try {
                    execute(sendMessageInfo);
                    Message sendedMessage = execute(sendMessage);

                    Timer timerForDeleteMessage = new Timer(120000, null);

                    timerForDeleteMessage.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            DeleteMessage deleteMessage = DeleteMessage
                                    .builder()
                                    .messageId(sendedMessage.getMessageId())
                                    .chatId(chatId)
                                    .build();
                            try {
                                execute(deleteMessage);
                            } catch (TelegramApiException ex) {
                                throw new RuntimeException(ex);
                            }

                        }
                    });

                    timerForDeleteMessage.setRepeats(false);
                    timerForDeleteMessage.setDelay(120000);
                    timerForDeleteMessage.start();

                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
            // Пошла категория 2 ------------------------
        } else if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals("Category2")) {
            user = userRepository.findByChatId(update.getCallbackQuery().getMessage().getChatId());
            user.setCategory("category2");
            user.setRegistrationDate(LocalDateTime.now());
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
            SendMessage sendMessage2 = SendMessage
                    .builder()
                    .replyMarkup(replyKeyboardMarkup)
                    .text("Пользователь " + user.getName() + " совершил оплату")
                    .chatId(secondAdminId)
                    .build();
            try {
                execute(sendMessage);
                execute(sendMessage2);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        } else if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals("Подтверждение Category2")) {
            user = userRepository.findByChatId(chatId);
            if (!user.getState().equals("approved")) {

                SendMessage sendMessageInfo = SendMessage
                        .builder()
                        .text(String.format("Видео будет доступно 30 дней после покупки!%nВозможно, для просмотра видео понадобится включить впн, рекомендуемая страна - Франция"))
                        .chatId(chatId)
                        .build();
                SendMessage sendMessage = SendMessage
                        .builder()
                        .text(String.format("Ссылка на видео в YouTube%nhttps://youtu.be/iZ650tzI2nI"))
                        .chatId(chatId)
                        .build();

                user.setState("approved");
                user.setStartWait(false);
                user.setViewedVideo(false);
                userRepository.save(user);

                Timer timerForMessageAfter = new Timer(5000, null);

                timerForMessageAfter.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        SendMessage sendMessage1 = SendMessage
                                .builder()
                                .text(String.format("Как всё прошло? Какие ощущения? Проект создан от всего сердца.%n" +
                                        "Передай самым дорогим людям вход в P O R T A L https://t.me/portalnewlife"))
                                .chatId(chatId)
                                .build();
                        try {
                            execute(sendMessage1);
                            userRepository.delete(user);
                        } catch (TelegramApiException ex) {
                            throw new RuntimeException(ex);
                        }

                    }
                });

                timerForMessageAfter.setRepeats(false);
                timerForMessageAfter.setDelay(5000); //5 sec
                timerForMessageAfter.start();
                try {
                    execute(sendMessageInfo);
                    Message sendedMessage = execute(sendMessage);

                    Timer timerForDeleteMessage = new Timer(120000, null);

                    timerForDeleteMessage.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            DeleteMessage deleteMessage = DeleteMessage
                                    .builder()
                                    .messageId(sendedMessage.getMessageId())
                                    .chatId(chatId)
                                    .build();
                            try {
                                execute(deleteMessage);
                            } catch (TelegramApiException ex) {
                                throw new RuntimeException(ex);
                            }

                        }
                    });

                    timerForDeleteMessage.setRepeats(false);
                    timerForDeleteMessage.setDelay(120000); //5 sec
                    timerForDeleteMessage.start();

                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        //Пошла третья ======

        else if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals("Category3")) {
            user = userRepository.findByChatId(update.getCallbackQuery().getMessage().getChatId());
            user.setCategory("category3");
            user.setRegistrationDate(LocalDateTime.now());
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
            SendMessage sendMessage2 = SendMessage
                    .builder()
                    .replyMarkup(replyKeyboardMarkup)
                    .text("Пользователь " + user.getName() + " совершил оплату")
                    .chatId(secondAdminId)
                    .build();
            try {
                execute(sendMessage);
                execute(sendMessage2);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        } else if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals("Подтверждение Category3")) {
            user = userRepository.findByChatId(chatId);
            if (!user.getState().equals("approved")) {

                SendMessage sendMessageInfo = SendMessage
                        .builder()
                        .text(String.format("Видео будет доступно 30 дней после покупки!%nВозможно, для просмотра видео понадобится включить впн, рекомендуемая страна - Франция"))
                        .chatId(chatId)
                        .build();
                SendMessage sendMessage = SendMessage
                        .builder()
                        .text(String.format("Ссылка на видео в YouTube%nhttps://youtu.be/icr_THOz3Vk"))
                        .chatId(chatId)
                        .build();

                user.setState("approved");
                user.setStartWait(false);
                user.setViewedVideo(false);
                userRepository.save(user);

                Timer t = new Timer(5000, null);

                t.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        SendMessage sendMessage1 = SendMessage
                                .builder()
                                .text(String.format("Как всё прошло? Какие ощущения? Проект создан от всего сердца.%n" +
                                        "Передай самым дорогим людям вход в P O R T A L https://t.me/portalnewlife"))
                                .chatId(chatId)
                                .build();
                        try {
                            execute(sendMessage1);
                            userRepository.delete(user);
                        } catch (TelegramApiException ex) {
                            throw new RuntimeException(ex);
                        }

                    }
                });

                t.setRepeats(false);
                t.setDelay(5000); //5 sec
                t.start();
                try {
                    execute(sendMessageInfo);
                    Message sendedMessage = execute(sendMessage);

                    Timer timerForDeleteMessage = new Timer(120000, null);

                    timerForDeleteMessage.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            DeleteMessage deleteMessage = DeleteMessage
                                    .builder()
                                    .messageId(sendedMessage.getMessageId())
                                    .chatId(chatId)
                                    .build();
                            try {
                                execute(deleteMessage);
                            } catch (TelegramApiException ex) {
                                throw new RuntimeException(ex);
                            }

                        }
                    });

                    timerForDeleteMessage.setRepeats(false);
                    timerForDeleteMessage.setDelay(120000);
                    timerForDeleteMessage.start();
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        // Четвертая категория -------------------------


        else if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals("Category4")) {
            user = userRepository.findByChatId(update.getCallbackQuery().getMessage().getChatId());
            user.setCategory("category4");
            user.setRegistrationDate(LocalDateTime.now());
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
            SendMessage sendMessage2 = SendMessage
                    .builder()
                    .replyMarkup(replyKeyboardMarkup)
                    .text("Пользователь " + user.getName() + " совершил оплату")
                    .chatId(secondAdminId)
                    .build();
            try {
                execute(sendMessage);
                execute(sendMessage2);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        } else if (update.hasCallbackQuery() && update.getCallbackQuery().getData().equals("Подтверждение Category4")) {
            user = userRepository.findByChatId(chatId);
            if (!user.getState().equals("approved")) {

                SendMessage sendMessageInfo = SendMessage
                        .builder()
                        .text(String.format("Видео будет доступно 30 дней после покупки!%nВозможно, для просмотра видео понадобится включить впн, рекомендуемая страна - Франция"))
                        .chatId(chatId)
                        .build();
                SendMessage sendMessage = SendMessage
                        .builder()
                        .text(String.format("Ссылка на видео в YouTube%nhttps://youtu.be/qXXgVOmXT1Y"))
                        .chatId(chatId)
                        .build();

                user.setState("approved");
                user.setStartWait(false);
                user.setViewedVideo(false);
                userRepository.save(user);

                Timer t = new Timer(5000, null);

                t.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        SendMessage sendMessage1 = SendMessage
                                .builder()
                                .text(String.format("Как всё прошло? Какие ощущения? Проект создан от всего сердца.%n" +
                                        "Передай самым дорогим людям вход в P O R T A L https://t.me/portalnewlife"))
                                .chatId(chatId)
                                .build();
                        try {
                            execute(sendMessage1);
                            userRepository.delete(user);
                        } catch (TelegramApiException ex) {
                            throw new RuntimeException(ex);
                        }

                    }
                });

                t.setRepeats(false);
                t.setDelay(5000); //5 sec
                t.start();
                try {
                    execute(sendMessageInfo);
                    Message sendedMessage = execute(sendMessage);

                    Timer timerForDeleteMessage = new Timer(120000, null);

                    timerForDeleteMessage.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            DeleteMessage deleteMessage = DeleteMessage
                                    .builder()
                                    .messageId(sendedMessage.getMessageId())
                                    .chatId(chatId)
                                    .build();
                            try {
                                execute(deleteMessage);
                            } catch (TelegramApiException ex) {
                                throw new RuntimeException(ex);
                            }

                        }
                    });

                    timerForDeleteMessage.setRepeats(false);
                    timerForDeleteMessage.setDelay(120000); //5 sec
                    timerForDeleteMessage.start();
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}




