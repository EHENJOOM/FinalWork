package com.zhk.panel.teacher.check;

import com.zhk.constant.Config;
import com.zhk.event.EventCenter;
import com.zhk.event.EventListener;
import com.zhk.event.Events;
import com.zhk.login.LoginBean;
import com.zhk.panel.student.subject.MatchBean;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 赵洪苛
 * @date 2019/11/25 20:43
 * @description 确认学生选课题面板
 */
public class CheckSubjectPanel extends JPanel implements CheckSubjectView, EventListener {

    private CheckSubjectAdapter adapter;
    private JTable table;
    private JComboBox<String> ofAcademyCombo;
    private JComboBox<String> studentAcademyCombo;


    private List<MatchBean> matchBeans;
    private CheckSubjectPresenter presenter = new CheckSubjectPresenter();

    public CheckSubjectPanel(LoginBean loginBean) {
        initView();
        presenter.attachView(this);
        presenter.select(loginBean);
        EventCenter.registerEventListener(this, new String[]{Events.ACCEPT_STUDENT, Events.REFUSE_STUDENT});
    }

    private void initView() {
        setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane();
        adapter = new CheckSubjectAdapter();
        table = new JTable(adapter);
        scrollPane.setViewportView(table);

        table.getColumnModel().getColumn(13).setCellRenderer(new CheckSubjectRender("接收"));
        table.getColumnModel().getColumn(13).setCellEditor(new CheckSubjectEditor("接收"));
        table.getColumnModel().getColumn(14).setCellRenderer(new CheckSubjectRender("拒绝"));
        table.getColumnModel().getColumn(14).setCellEditor(new CheckSubjectEditor("拒绝"));

        JPanel headerPanel = new JPanel(new GridLayout(1, 5, 10, 20));
        JLabel ofAcademyLabel = new JLabel("课题所属学院");
        ofAcademyCombo = new JComboBox<>();
        JLabel studentAcademy = new JLabel("学生所属学院");
        studentAcademyCombo = new JComboBox<>();
        JButton filterButton = new JButton("查找");

        for (String item : Config.ACADEMY) {
            ofAcademyCombo.addItem(item);
            studentAcademyCombo.addItem(item);
        }

        ofAcademyLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        studentAcademy.setHorizontalAlignment(SwingConstants.RIGHT);
        filterButton.addActionListener(event -> filter());
        headerPanel.add(ofAcademyLabel);
        headerPanel.add(ofAcademyCombo);
        headerPanel.add(studentAcademy);
        headerPanel.add(studentAcademyCombo);
        headerPanel.add(filterButton);

        add(scrollPane, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);
    }

    private void filter() {
        String ofAcademy = (String) ofAcademyCombo.getSelectedItem();
        String studentAcademy = (String) studentAcademyCombo.getSelectedItem();

        List<MatchBean> list = matchBeans;
        if (ofAcademy != null && !ofAcademy.isEmpty()) {
            list = list.parallelStream()
                    .filter(matchBean -> matchBean.getSubjectBean().getOfAcademy().contentEquals(ofAcademy)
                            || matchBean.getSubjectBean().getOfAcademy().equals(ofAcademy))
                    .collect(Collectors.toList());
        }
        if (studentAcademy != null && !studentAcademy.isEmpty()) {
            list = list.parallelStream()
                    .filter(matchBean ->matchBean.getStudentBean().getAcademy().contains(studentAcademy)
                            || matchBean.getStudentBean().getAcademy().equals(studentAcademy))
                    .collect(Collectors.toList());
        }

        if (list.size() == 0) {
            JOptionPane.showMessageDialog(this, "没有找到符合条件的记录", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        adapter.setMatchBeans(list);
        table.updateUI();
    }

    @Override
    public void update(List<MatchBean> matchBeans) {
        SwingUtilities.invokeLater(() -> {
            this.matchBeans = matchBeans;
            adapter.setMatchBeans(matchBeans);
            table.updateUI();
        });
    }

    @Override
    public void showMessage(String msg, MatchBean data) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this, msg, "提示", JOptionPane.INFORMATION_MESSAGE);
            matchBeans.forEach(matchBean -> {
                if (matchBean.getId() == data.getId()) {
                    matchBean.setState(data.getState());
                }
            });
            table.updateUI();
        });
    }

    @Override
    public void resetState(MatchBean matchBean) {
        SwingUtilities.invokeLater(() -> {
            matchBean.setState(Config.CONFIRMING_SUBJECT);
            table.updateUI();
        });
    }

    @Override
    public void onEvent(String topic, int msgCode, int resultCode, Object object) {
        switch (topic) {
            case Events.ACCEPT_STUDENT:
            case Events.REFUSE_STUDENT:
                presenter.check(topic, (MatchBean)object);
                break;
            default:
        }
    }
}
