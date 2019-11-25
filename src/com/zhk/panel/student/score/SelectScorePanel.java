package com.zhk.panel.student.score;

import com.zhk.login.LoginBean;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 赵洪苛
 * @date 2019/11/24 16:07
 * @description 查询学生成绩面板
 */
public class SelectScorePanel extends JPanel implements SelectScoreView {

    private JTable table;
    private ScoreAdapter adapter;

    private JTextField teacherText;
    private JTextField courseText;

    private List<ScoreBean> scoreBeans;
    private SelectScorePresenter presenter = new SelectScorePresenter();

    public SelectScorePanel(LoginBean loginBean) {
        initView();
        presenter.attachView(this);
        presenter.select(loginBean);
    }

    private void initView() {
        setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane();
        adapter = new ScoreAdapter();
        table = new JTable(adapter);
        scrollPane.setViewportView(table);

        JPanel headerPanel = new JPanel(new GridLayout(1, 5, 10, 20));
        JLabel teacherLabel = new JLabel("任课教师");
        teacherText = new JTextField();
        JLabel courseLabel = new JLabel("课程名称");
        courseText = new JTextField();
        JButton selectButton = new JButton("查询");
        selectButton.addActionListener(event -> filter());

        teacherLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        courseLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        selectButton.setHorizontalAlignment(SwingConstants.CENTER);

        headerPanel.add(teacherLabel);
        headerPanel.add(teacherText);
        headerPanel.add(courseLabel);
        headerPanel.add(courseText);
        headerPanel.add(selectButton);

        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void filter() {
        String teacher = teacherText.getText();
        String course = courseText.getText();

        List<ScoreBean> list = scoreBeans;
        if (!teacher.isEmpty()) {
            list = list.parallelStream()
                    .filter(scoreBean -> scoreBean.getTeacherBean().getName().equals(teacher))
                    .collect(Collectors.toList());
        }
        if (!course.isEmpty()) {
            list = list.parallelStream()
                    .filter(scoreBean -> scoreBean.getName().equals(course))
                    .collect(Collectors.toList());
        }

        if (list.size() == 0) {
            JOptionPane.showMessageDialog(this, "没有找到符合条件的成绩", "提示", JOptionPane.INFORMATION_MESSAGE);
        } else {
            adapter.setScoreBeans(list);
            table.updateUI();
        }
    }

    @Override
    public void update(List<ScoreBean> list) {
        SwingUtilities.invokeLater(() -> {
            this.scoreBeans = list;
            adapter.setScoreBeans(list);
            table.updateUI();
        });
    }
}
