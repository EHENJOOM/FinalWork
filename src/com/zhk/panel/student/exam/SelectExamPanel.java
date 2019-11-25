package com.zhk.panel.student.exam;

import com.zhk.login.LoginBean;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @author 赵洪苛
 * @date 2019/11/25 11:38
 * @description 查询考试信息面板
 */
public class SelectExamPanel extends JPanel implements SelectExamView {

    private ExamAdapter adapter;
    private JTable table;

    private SelectExamPresenter presenter = new SelectExamPresenter();

    public SelectExamPanel(LoginBean loginBean) {
        initView();
        presenter.attachView(this);
        presenter.select(loginBean);
    }

    private void initView() {
        setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane();
        adapter = new ExamAdapter();
        table = new JTable(adapter);
        scrollPane.setViewportView(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    @Override
    public void update(List<ExamBean> examBeans) {
        SwingUtilities.invokeLater(() -> {
            adapter.setExamBeans(examBeans);
            table.updateUI();
        });
    }
}
