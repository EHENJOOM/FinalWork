package com.zhk.panel.student.subject;

import com.zhk.event.EventCenter;
import com.zhk.event.EventListener;
import com.zhk.event.Events;
import com.zhk.login.LoginBean;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 赵洪苛
 * @date 2019/11/23 17:55
 * @description 学生选课题面板
 */
public class SelectSubjectPanel extends JPanel implements SelectSubjectView, EventListener {

    private SelectSubjectAdapter selectSubjectAdapter;
    private JTable table;
    private JComboBox<String> ofAcademyCombo;
    private JTextField subjectTextField;
    private JTextField teacherTextField;

    private LoginBean loginBean;
    private List<SubjectBean> subjectBeans;
    private SelectSubjectPresenter presenter = new SelectSubjectPresenter();

    public SelectSubjectPanel(LoginBean loginBean) {
        this.loginBean = loginBean;
        initView();
        presenter.attachView(this);
        presenter.select(loginBean);
        EventCenter.registerEventListener(this, new String[]{Events.INSERT_MATCH, Events.CANCEL_MATCH});
    }

    private void initView() {
        setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane();
        selectSubjectAdapter = new SelectSubjectAdapter();
        selectSubjectAdapter.setStudentBean(loginBean);
        table = new JTable(selectSubjectAdapter);
        table.getColumnModel().getColumn(10).setCellEditor(new SelectButtonEditor());
        table.getColumnModel().getColumn(10).setCellRenderer(new SelectButtonRender());
        table.setRowHeight(25);
        scrollPane.setViewportView(table);

        JLabel ofAcademyLabel = new JLabel("课题主管学院");
        JLabel subjectLabel = new JLabel("课题名称");
        JLabel teacherLabel = new JLabel("指导教师");
        ofAcademyCombo = new JComboBox<>();
        subjectTextField = new JTextField();
        teacherTextField = new JTextField();
        JButton selectButton = new JButton("查询");

        ofAcademyLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        subjectLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        teacherLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        selectButton.setHorizontalAlignment(SwingConstants.CENTER);

        selectButton.addActionListener(event -> filter());
        String[] items = new String[]{null, "经济管理学院", "信息科技与技术学院", "马克思主义学院", "文法学院"};
        for (String item : items) {
            ofAcademyCombo.addItem(item);
        }

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new GridLayout(1 ,7, 10, 20));
        headerPanel.add(ofAcademyLabel);
        headerPanel.add(ofAcademyCombo);
        headerPanel.add(subjectLabel);
        headerPanel.add(subjectTextField);
        headerPanel.add(teacherLabel);
        headerPanel.add(teacherTextField);
        headerPanel.add(selectButton);

        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void filter() {
        String of = (String) ofAcademyCombo.getSelectedItem();
        String subject = subjectTextField.getText();
        String teacher = teacherTextField.getText();

        List<SubjectBean> list = subjectBeans;
        if (of != null && !of.isEmpty()) {
            list = subjectBeans.parallelStream()
                    .filter(subjectBean -> subjectBean.getOfAcademy().equals(of))
                    .collect(Collectors.toList());
        }
        if (!subject.isEmpty()) {
            list = list.parallelStream()
                    .filter(subjectBean -> subjectBean.getName().equals(subject))
                    .collect(Collectors.toList());
        }
        if (!teacher.isEmpty()) {
            list = list.parallelStream()
                    .filter(subjectBean -> subjectBean.getTeacherBean().getName().equals(teacher))
                    .collect(Collectors.toList());
        }

        if (list.size() == 0) {
            JOptionPane.showMessageDialog(this, "没有找到匹配的课题！", "提示", JOptionPane.INFORMATION_MESSAGE);
        } else {
            selectSubjectAdapter.setSubjectBeans(list);
            table.updateUI();
        }
    }

    @Override
    public void update(List<SubjectBean> subjectBeans) {
        SwingUtilities.invokeLater(() -> {
            this.subjectBeans = subjectBeans;
            selectSubjectAdapter.setSubjectBeans(subjectBeans);
            table.updateUI();
        });
    }

    @Override
    public void showMessage(String msg, MatchBean data) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(null, msg, "提示", JOptionPane.INFORMATION_MESSAGE);
            subjectBeans.forEach(subjectBean -> {
                if (data.getSubjectBean().getCode().equals(subjectBean.getCode())) {
                    subjectBean.setState(data.getState());
                }
            });

            table.updateUI();
        });
    }

    @Override
    public void onEvent(String topic, int msgCode, int resultCode, Object object) {
        switch (topic) {
            case Events.INSERT_MATCH:
                presenter.insert((MatchBean) object);
                break;
            case Events.CANCEL_MATCH:
                presenter.cancel((MatchBean) object);
                break;
            default:
        }
    }
}
